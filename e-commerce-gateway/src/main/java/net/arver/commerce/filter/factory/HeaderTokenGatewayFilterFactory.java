package net.arver.commerce.filter.factory;

import net.arver.commerce.filter.HeaderTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * HeaderTokenGatewayFilterFactory.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Component
public class HeaderTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(final Object config) {
        return new HeaderTokenGatewayFilter();
    }
}
