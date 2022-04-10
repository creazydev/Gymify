package com.github.Gymify.core.filter;

import com.github.Gymify.core.filter.expression.IntExpression;
import com.github.Gymify.core.filter.expression.LongExpression;
import com.github.Gymify.core.filter.expression.StringExpression;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class GraphQLFilterChain extends GraphQLFilter {
    private final GraphQLFilterChain and;
    private final GraphQLFilterChain or;
    private final GraphQLFilterChain not;

    public GraphQLFilterChain(String[] path, StringExpression stringExpression, LongExpression longExpression,
                              IntExpression intExpression, GraphQLFilterChain and, GraphQLFilterChain or,
                              GraphQLFilterChain not) {
        super(path, stringExpression, longExpression, intExpression);
        this.and = and;
        this.or = or;
        this.not = not;
    }
}
