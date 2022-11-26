package net.arver.commerce.config;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * DynamicRouteServiceImpl.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    /**
     * 构造函数.
     * @param routeDefinitionWriter 写路由定义
     * @param routeDefinitionLocator 读路由定义
     */
    public DynamicRouteServiceImpl(final RouteDefinitionWriter routeDefinitionWriter,
                                   final RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    /**
     * 写路由定义.
     */
    private final RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 读路由定义.
     */
    private final RouteDefinitionLocator routeDefinitionLocator;

    /**
     * 事件发布.
     */
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 增加路由定义.
     * @param routeDefinition 路由定义
     * @return 操作结果
     */
    public String addRouteDefinition(final RouteDefinition routeDefinition) {
        log.info("gateway add route: [{}]", routeDefinition);

        // 保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        return "success";
    }

    public String updateList(final List<RouteDefinition> definitions) {
        log.info("gateway update route: [{}]", definitions);

        // 先拿到当前Gateway中存储的路由定义
        final List<RouteDefinition> oldDefinitions = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (CollectionUtils.isNotEmpty(oldDefinitions)) {
            // 清除之前的路由定义
            oldDefinitions.forEach((item) -> {
                log.info("delete route definition: [{}]", item);
                deleteById(item.getId());
            });
        }

        // 把新的路由定义同步到 gateway 中
        definitions.forEach(item -> updateByRouteDefinition(item));
        return "success";
    }

    /**
     * 根据 id 删除路由定义.
     * @param id id
     * @return 操作结果
     */
    private String deleteById(final String id) {
        try {
            log.info("gateway delete route id: [{}]", id);
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            // 发布事件通知给 gateway 更新路由定义
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";
        } catch (Exception e) {
            log.error("gateway delete route fail: [{}]", e.getMessage(), e);
            return "delete fail";
        }
    }

    /**
     * 更新路由定义.
     * @param definition 路由定义
     * @return 操作结果
     */
    private String updateByRouteDefinition(final RouteDefinition definition) {
        try {
            log.info("gateway update route: [{}]", definition);
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail, not find route routeId:" + definition.getId();
        }

        try {
            this.routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route fail";
        }
    }
}
