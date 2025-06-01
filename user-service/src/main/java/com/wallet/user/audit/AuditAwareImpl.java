package com.wallet.user.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware {

    /**
     * @return
     */
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("USER_MS");
    }
}
