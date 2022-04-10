package com.github.Gymify.core.filter;

import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class GraphQLSpecificationFilter<T> extends GraphQLFilter implements SpecificationExtractor<T> {
    private SpecificationExtractor<T> and;
    private SpecificationExtractor<T> or;
    private SpecificationExtractor<T> not;

    public GraphQLSpecificationFilter(GraphQLFilterChain graphQLFilterChain) {
        super(
            graphQLFilterChain.getPath(),
            graphQLFilterChain.getStringExpression(),
            graphQLFilterChain.getLongExpression(),
            graphQLFilterChain.getIntExpression()
        );

        if (Objects.nonNull(graphQLFilterChain.getAnd())) {
            this.and = new GraphQLSpecificationFilter<>(graphQLFilterChain.getAnd());
        }

        if (Objects.nonNull(graphQLFilterChain.getOr())) {
            this.or = new GraphQLSpecificationFilter<>(graphQLFilterChain.getOr());
        }

        if (Objects.nonNull(graphQLFilterChain.getNot())) {
            this.not = new GraphQLSpecificationFilter<>(graphQLFilterChain.getNot());
        }
    }

    public Specification<T> getSpecification() {
        ValueExpressionSpecificationExtractor<T, ?> filterExpression = this.getFilterExpression();
        if (Objects.isNull(filterExpression)) {
            return Specification.where(null);
        }
        Specification<T> specification = filterExpression.getSpecification(super.getPath());
        if (Objects.nonNull(this.and)) {
            specification = specification.and(and.getSpecification());
        }
        if (Objects.nonNull(this.or)) {
            specification = specification.or(and.getSpecification());
        }
        if (Objects.nonNull(this.not)) {
            specification = specification.or(not.getSpecification());
        }
        return specification;
    }

    private ValueExpressionSpecificationExtractor<T, ?> getFilterExpression() {
        if (Objects.nonNull(super.getStringExpression())) {
            return new StringExpressionSpecificationExtractor<>(super.getStringExpression());
        } else if (Objects.nonNull(super.getLongExpression())) {
            return new LongExpressionSpecificationExtractor<>(super.getLongExpression());
        } else if (Objects.nonNull(super.getIntExpression())) {
            return new IntExpressionSpecificationExtractor<>(super.getIntExpression());
        } else {
            return null;
        }
    }
}
