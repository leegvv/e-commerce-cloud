package net.arver.commerce.filter;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.constant.CommonConstant;
import net.arver.commerce.constant.GatewayConstant;
import net.arver.commerce.util.TokenUtil;
import net.arver.commerce.vo.LoginUserInfo;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * GlobalLoginOrRegisterFilter.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Component
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {

    /**
     * 注册中心客户端，可以从注册中心中获取服务示例信息.
     */
    private final LoadBalancerClientFactory clientFactory;

    /**
     * restTemplate.
     */
    private final RestTemplate restTemplate;

    /**
     * 构造函数.
     * @param clientFactory 注册中心客户端工厂
     * @param restTemplate restTemplate
     */
    public GlobalLoginOrRegisterFilter(final LoadBalancerClientFactory clientFactory, final RestTemplate restTemplate) {
        this.clientFactory = clientFactory;
        this.restTemplate = restTemplate;
    }

    /**
     * 登陆、注册、授权.
     *
     * @param exchange the current server exchange
     * @param chain provides a way to delegate to the next filter
     * @return 过滤器
     */
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final ServerHttpResponse response = exchange.getResponse();
        final String path = request.getURI().getPath();

        //1. 如果是登录/注册
        if (path.contains(GatewayConstant.LOGIN_URI) || path.contains(GatewayConstant.REGISTER_URI)) {
            return chain.filter(exchange);
        }

        // 2. 访问其他的服务，则鉴权，校验是否能否从 Token 中解析出用户信息
        final HttpHeaders headers = request.getHeaders();
        final String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenUtil.parseUserInfoFromToken(token);
        } catch (Exception e) {
            log.error("parse user info from token error: [{}]", e.getMessage(), e);
        }

        // 获取不到用户信息，返回401
        if (loginUserInfo == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 解析通过，放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }
}
