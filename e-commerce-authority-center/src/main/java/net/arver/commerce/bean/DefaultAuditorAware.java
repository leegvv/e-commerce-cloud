package net.arver.commerce.bean;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

/**
 * DefaultAuditorAware.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public class DefaultAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(1L);
    }
}
