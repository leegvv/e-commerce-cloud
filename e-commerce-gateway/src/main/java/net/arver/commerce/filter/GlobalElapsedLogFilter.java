package net.arver.commerce.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * GlobalElapsedLogFilter.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Component
public class GlobalElapsedLogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        // 前置逻辑
        final StopWatch sw = new StopWatch();
        final String uri = exchange.getRequest().getURI().getPath();

        return chain.filter(exchange).then(
                // 后置逻辑
                Mono.fromRunnable(() -> {
                    log.info("[{}] elaspsed: [{}ms]", uri, sw.getTotalTimeMillis());
                })
        );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
