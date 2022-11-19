package net.arver.commerce.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * AdminEventNotifier.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Component
@SuppressWarnings("all")
public class AdminEventNotifier extends AbstractEventNotifier {
    protected AdminEventNotifier(InstanceRepository repository) {
        super(repository);
    }

    /**
     * 事件通知.
     * @param event 事件
     * @param instance 实例
     * @return void
     */
    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                log.info("Instance Status Change: [{}], [{}], [{}]", instance.getRegistration().getName(),
                        event.getInstance(), ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
                // 发送邮件、消息等
            } else {
                log.info("Instance Info: [{}], [{}], [{}]", instance.getRegistration().getName(),
                        event.getInstance(), event.getType());
            }
        });
    }
}
