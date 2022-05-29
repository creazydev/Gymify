package com.github.Gymify.core.filter.expression;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IntExpression implements ValueExpression {
    private final Integer eq;
    private final Integer gt;
    private final Integer gte;
    private final Integer lt;
    private final Integer lte;
}
