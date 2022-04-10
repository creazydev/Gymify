package com.github.Gymify.core.filter.expression;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class StringExpression implements ValueExpression {
    private final String equals;
    private final String contains;
}
