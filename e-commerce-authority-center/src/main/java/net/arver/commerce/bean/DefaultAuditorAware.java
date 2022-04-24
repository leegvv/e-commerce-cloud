package net.arver.commerce.bean;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

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
