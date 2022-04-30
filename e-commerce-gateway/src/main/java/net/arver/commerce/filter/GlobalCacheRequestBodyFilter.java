package net.arver.commerce.filter;

import net.arver.commerce.config.GatewayConfig;
import net.arver.commerce.constant.GatewayConstant;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 缓存请求 body 的全局过滤器.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Component
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {

        final String path = exchange.getRequest().getURI().getPath();
        boolean isLoginOrRegister = path.contains(GatewayConstant.LOGIN_URI)
                || path.contains(GatewayConstant.REGISTER_URI);

        if (null == exchange.getRequest().getHeaders().getContentType() || !isLoginOrRegister) {
            return chain.filter(exchange);
        }

        // DataBufferUtils.join 拿到请求中的数据 --> DataBuffer

        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            // 确保数据缓冲区不被释放， 必须要 DataBufferUtils.retain
            DataBufferUtils.retain(dataBuffer);
            // defer、just都是去创建数据源，得到当前数据的副本
            final Flux<DataBuffer> cachedFlux = Flux.defer(() ->
                    Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
            // 重新包装 ServerHttpRequest，重写 getBody 方法， 能够返回请求数据
            final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()){
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        });
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}
