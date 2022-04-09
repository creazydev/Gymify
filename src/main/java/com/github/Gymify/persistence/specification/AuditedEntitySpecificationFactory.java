package com.github.Gymify.persistence.specification;

import com.github.Gymify.persistence.auditing.AuditedEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public abstract class AuditedEntitySpecificationFactory<T extends AuditedEntity> {

    public Specification<T> createdDateTimeAfter(LocalDateTime dateTime) {
        return (chat, cq, cb) -> cb.greaterThanOrEqualTo(chat.get("createdDate"), dateTime);
    }

    public Specification<T> createdDateTimeBefore(LocalDateTime dateTime) {
        return (chat, cq, cb) -> cb.lessThanOrEqualTo(chat.get("createdDate"), dateTime);
    }
}
