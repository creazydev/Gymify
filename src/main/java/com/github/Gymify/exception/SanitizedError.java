package com.github.Gymify.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ExceptionWhileDataFetching;
import graphql.execution.ResultPath;
import graphql.language.SourceLocation;

public class SanitizedError extends ExceptionWhileDataFetching {

    public SanitizedError(ExceptionWhileDataFetching inner) {
        super(ResultPath.fromList(inner.getPath()), inner.getException(), SourceLocation.EMPTY);
    }

    public SanitizedError(RuntimeExceptionWhileDataFetching inner) {
        super(ResultPath.fromList(inner.getPath()), inner, SourceLocation.EMPTY);
    }

    @Override
    @JsonIgnore
    public Throwable getException() {
        return super.getException();
    }
}
