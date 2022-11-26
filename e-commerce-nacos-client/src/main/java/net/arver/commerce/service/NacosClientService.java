package net.arver.commerce.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

/**
 * NacosClientService.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Service
public class NacosClientService {

    private final DiscoveryClient discoveryClient;

    public NacosClientService(final DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * 获取Nacos Client.
     * @param serviceId 服务名称
     * @return 服务实例
     */
    public List<ServiceInstance> getNacosClientInfo(final String serviceId) {
        log.info("request nacos client to get service instance info: [{}]", serviceId);
        return discoveryClient.getInstances(serviceId);
    }
}
