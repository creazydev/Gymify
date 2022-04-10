package com.github.Gymify.core.filter;

import com.github.Gymify.core.filter.expression.IntExpression;
import com.github.Gymify.core.filter.expression.LongExpression;
import com.github.Gymify.core.filter.expression.StringExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
class GraphQLFilter {
    private final String[] path;
    private final StringExpression stringExpression;
    private final LongExpression longExpression;
    private final IntExpression intExpression;
}
