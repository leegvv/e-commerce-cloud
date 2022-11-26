package net.arver.commerce.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * GatewayBeanConf.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Configuration
public class GatewayBeanConf {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
