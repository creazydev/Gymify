package com.github.Gymify.core.filter;

import com.github.Gymify.core.filter.expression.StringExpression;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class StringExpressionSpecificationExtractor<T> implements ValueExpressionSpecificationExtractor<T, String> {
    private final StringExpression stringExpression;

    @Override
    public Specification<T> getSpecification(String... path) {
        if (Strings.isNotBlank(this.stringExpression.getEquals())) {
            return (root, cq, cb) -> cb.equal(getPath(root, path), this.stringExpression.getEquals());
        } else if (Strings.isNotBlank(this.stringExpression.getContains())) {
            return (root, cq, cb) -> cb.like(getPath(root, path), this.stringExpression.getContains());
        } else {
            return Specification.where(null);
        }
    }
}
