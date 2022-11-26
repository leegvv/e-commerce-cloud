package net.arver.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * AuthorityCenterApplication.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@EnableJpaAuditing //允许 jpa 自动审计
@EnableDiscoveryClient
@SpringBootApplication
public class AuthorityCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityCenterApplication.class, args);
    }
}
