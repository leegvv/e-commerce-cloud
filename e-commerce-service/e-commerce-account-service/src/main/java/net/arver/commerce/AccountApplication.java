package net.arver.commerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * AccountApplication.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@OpenAPIDefinition(
        info = @Info(
                title = "账户服务",
                version = "1.0",
                description = "账户服务API接口信息",
                contact = @Contact(name="arver"),
                termsOfService = "http://arver/account/**",
                license = @License(name = "1.0")
        )
)
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaAuditing
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
