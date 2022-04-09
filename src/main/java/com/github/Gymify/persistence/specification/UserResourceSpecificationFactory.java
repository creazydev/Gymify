package com.github.Gymify.persistence.specification;

import com.github.Gymify.persistence.auditing.AuditedEntity;
import com.github.Gymify.persistence.entity.UserResource;
import org.springframework.data.jpa.domain.Specification;

public abstract class UserResourceSpecificationFactory<T extends UserResource> extends AuditedEntitySpecificationFactory<T> {

    public Specification<T> userIdEquals(Long userId) {
        return (chat, cq, cb) -> cb.equal(chat.get("user").get("id"), userId);
    }
}
