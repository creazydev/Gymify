package com.github.Gymify.core.filter;

import com.github.Gymify.core.filter.expression.LongExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@RequiredArgsConstructor
public class LongExpressionSpecificationExtractor<T> implements ValueExpressionSpecificationExtractor<T, Long> {
    private final LongExpression longExpression;

    @Override
    public Specification<T> getSpecification(String... path) {
        if (Objects.nonNull(this.longExpression.getEq())) {
            return (root, cq, cb) -> cb.equal(getPath(root, path), this.longExpression.getEq());
        } else if (Objects.nonNull(this.longExpression.getGt())) {
            return (root, cq, cb) -> cb.greaterThan(getPath(root, path), this.longExpression.getGt());
        } else if (Objects.nonNull(this.longExpression.getGte())) {
            return (root, cq, cb) -> cb.greaterThanOrEqualTo(getPath(root, path), this.longExpression.getGte());
        } else if (Objects.nonNull(this.longExpression.getLt())) {
            return (root, cq, cb) -> cb.lessThan(getPath(root, path), this.longExpression.getLt());
        } else if (Objects.nonNull(this.longExpression.getLte())) {
            return (root, cq, cb) -> cb.lessThanOrEqualTo(getPath(root, path), this.longExpression.getLte());
//            } else if (Objects.nonNull(this.between)) {
//                return (root, cq, cb) -> cb.equal(getPath(root, path), this.between);
        } else {
            return Specification.where(null);
        }
    }
}
