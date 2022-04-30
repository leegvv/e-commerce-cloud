package net.arver.commerce.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.util.JsonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * DynamicRouteServiceImplByNacos.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Component
@DependsOn({"gatewayConfig"})
public class DynamicRouteServiceImplByNacos {

    /**
     * Nacos 配置服务.
     */
    private ConfigService configService;

    private final DynamicRouteServiceImpl dynamicRouteService;

    public DynamicRouteServiceImplByNacos(final DynamicRouteServiceImpl dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    /**
     * Bean 在容器中构造完成之后会执行 init 方法
     */
    @PostConstruct
    public void init() {
        log.info("gateway route init...");
        try{
            configService = initConfigService();
            if (configService == null) {
                log.error("init config service fail");
                return;
            }

            // 通过 Nacos Config 并指定路由配置路径去获取路由配置
            final String configInfo = configService.getConfig(
                    GatewayConfig.NACOS_ROUTE_DATA_ID,
                    GatewayConfig.NACOS_ROUTE_GROUP,
                    GatewayConfig.DEFAULT_TIMEOUT
            );
            log.info("get current gateway config: [{}]", configInfo);
            final List<RouteDefinition> definitionList =
                    JsonUtil.parse(configInfo, new TypeReference<List<RouteDefinition>>() {});
            if (CollectionUtils.isNotEmpty(definitionList)) {
                for (final RouteDefinition definition : definitionList) {
                    log.info("init gateway config: [{}]", definition.toString());
                    dynamicRouteService.addRouteDefinition(definition);
                }
            }
        } catch (final Exception e) {
            log.error("gateway route init has some error: [{}]", e.getMessage(), e);
        }

        // 设置监听器
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTE_DATA_ID, GatewayConfig.NACOS_ROUTE_GROUP);
    }

    /**
     * 初始化配置服务.
     * @return
     */
    private ConfigService initConfigService() {
        try {
            final Properties properties = new Properties();
            properties.setProperty("serverAddr", GatewayConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace", GatewayConfig.NACOS_NAMESPACE);
            return configService  = NacosFactory.createConfigService(properties);
        } catch (final Exception e) {
            log.error("init gateway nacos config error: [{}]", e.getMessage(), e);
            return null;
        }
    }

    private void dynamicRouteByNacosListener(final String dataId, final String group) {
        try{
            // 给 Nacos Config 客户端增加一个监听器
            configService.addListener(dataId, group, new Listener() {

                /**
                 * 自己提供线程池执行操作，不提供则使用默认的
                 * @return
                 */
                @Override
                public Executor getExecutor() {
                    return null;
                }

                /**
                 * 监听器受到配置更新
                 * @param configInfo Nacos 中最新的配置定义
                 */
                @Override
                public void receiveConfigInfo(final String configInfo) {
                    log.info("start to update config: [{}]", configInfo);
                    final List<RouteDefinition> definitionList =
                            JsonUtil.parse(configInfo, new TypeReference<List<RouteDefinition>>() {});
                    log.info("update route: [{}]", definitionList.toString());
                    dynamicRouteService.updateList(definitionList);
                }
            });

        } catch (final Exception e) {
            log.error("gateway add listener fail:[{}], [{}]", dataId, group);
        }
    }

}
