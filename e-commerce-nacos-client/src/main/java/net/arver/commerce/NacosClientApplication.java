package net.arver.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * NacosClientApplication.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosClientApplication.class, args);
    }

}
