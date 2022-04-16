package com.github.Gymify.persistence.specification;

import com.github.Gymify.persistence.auditing.AuditedEntity;
import org.springframework.data.jpa.domain.Specification;

public interface EntitySpecificationFactory<T, K> {

    Specification<T> idEquals(K id);
    Specification<T> idNotEquals(K id);
}
