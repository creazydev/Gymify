package com.github.Gymify.core.filter;

import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface SpecificationExtractor<T> {
    Specification<T> getSpecification();
}
