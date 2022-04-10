package com.github.Gymify.persistence.specification;

import com.github.Gymify.persistence.auditing.AuditedEntity;
import org.springframework.data.jpa.domain.Specification;

public abstract class AuditedEntitySpecificationFactory<T extends AuditedEntity> {

    public Specification<T> creationTimestampAfter(Long timestamp) {
        return (chat, cq, cb) -> cb.greaterThan(chat.get("creationTimestamp"), timestamp);
    }
}
