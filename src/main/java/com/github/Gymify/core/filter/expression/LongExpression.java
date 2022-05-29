package com.github.Gymify.core.filter.expression;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class LongExpression implements ValueExpression {
    private final Long eq;
    private final Long gt;
    private final Long gte;
    private final Long lt;
    private final Long lte;
    private final List<Long> between;
}
