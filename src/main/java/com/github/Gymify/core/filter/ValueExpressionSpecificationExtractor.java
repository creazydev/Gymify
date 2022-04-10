package com.github.Gymify.core.filter;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

@FunctionalInterface
interface ValueExpressionSpecificationExtractor<T, K> {
    Specification<T> getSpecification(String... path);

    default Path<K> getPath(Root<T> root, String... path) {
        Path<Object> p = (Path<Object>) root;
        for (String s : path) {
            p = p.get(s);
        }
        return (Path<K>) p;
    }

    default String getLowerCaseSearchTerm(String str) {
        return  "%" + str.toLowerCase().trim() + "%";
    }
}
