package net.arver.commerce.conf;

import net.arver.commerce.bean.DefaultAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * AuthorityCenterConfig.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Configuration
public class AuthorityCenterConfig {

    @Bean
    AuditorAware<Long> auditorAware() {
        return new DefaultAuditorAware();
    }
}
