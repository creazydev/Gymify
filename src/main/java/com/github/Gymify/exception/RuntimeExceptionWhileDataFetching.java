package com.github.Gymify.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class RuntimeExceptionWhileDataFetching extends ResponseStatusException implements GraphQLError {

    public RuntimeExceptionWhileDataFetching(HttpStatus status) {
        super(status);
    }

    public RuntimeExceptionWhileDataFetching(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static RuntimeExceptionWhileDataFetching notFound(Class c) {
        return new RuntimeExceptionWhileDataFetching(HttpStatus.NOT_FOUND, c.getSimpleName() + " could not be found");
    }

    public static RuntimeExceptionWhileDataFetching unAuthorized() {
        return new RuntimeExceptionWhileDataFetching(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return List.of();
    }

    @Override
    public ErrorClassification getErrorType() {
        return new ErrorClassification() {
            @Override
            public Object toSpecification(GraphQLError error) {
                return ErrorClassification.super.toSpecification(error);
            }
        };
    }
}
