package net.arver.commerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * GoodsApplication.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@OpenAPIDefinition(
        info = @Info(
                title = "商品服务",
                version = "1.0",
                description = "商品服务API接口信息",
                contact = @Contact(name="arver"),
                termsOfService = "http://arver/goods/**",
                license = @License(name = "1.0")
        )
)
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
