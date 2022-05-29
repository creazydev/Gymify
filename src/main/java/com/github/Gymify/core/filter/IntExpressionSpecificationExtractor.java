package com.github.Gymify.core.filter;

import com.github.Gymify.core.filter.expression.IntExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@RequiredArgsConstructor
public class IntExpressionSpecificationExtractor<T> implements ValueExpressionSpecificationExtractor<T, Integer> {
    private final IntExpression intExpression;

    @Override
    public Specification<T> getSpecification(String... path) {
        if (Objects.nonNull(this.intExpression.getEq())) {
            return (root, cq, cb) -> cb.equal(getPath(root, path), this.intExpression.getEq());
        } else if (Objects.nonNull(this.intExpression.getGt())) {
            return (root, cq, cb) -> cb.greaterThan(getPath(root, path), this.intExpression.getGt());
        } else if (Objects.nonNull(this.intExpression.getGte())) {
            return (root, cq, cb) -> cb.greaterThanOrEqualTo(getPath(root, path), this.intExpression.getGte());
        } else if (Objects.nonNull(this.intExpression.getLt())) {
            return (root, cq, cb) -> cb.lessThan(getPath(root, path), this.intExpression.getLt());
        } else if (Objects.nonNull(this.intExpression.getLte())) {
            return (root, cq, cb) -> cb.lessThanOrEqualTo(getPath(root, path), this.intExpression.getLte());
        } else {
            return Specification.where(null);
        }
    }
}
