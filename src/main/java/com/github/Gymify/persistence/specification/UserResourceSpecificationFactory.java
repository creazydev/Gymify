package com.github.Gymify.persistence.specification;

import com.github.Gymify.persistence.entity.UserResource;
import org.springframework.data.jpa.domain.Specification;

public abstract class UserResourceSpecificationFactory<T extends UserResource>
    extends AuditedEntitySpecificationFactory<T> implements EntitySpecificationFactory<T, Long> {

    @Override
    public Specification<T> idEquals(Long id) {
        return (root, cq, cb) -> cb.equal(root.get("id"), id);
    }

    public Specification<T> userIdEquals(Long userId) {
        return (root, cq, cb) -> cb.equal(root.get("user").get("id"), userId);
    }
}
