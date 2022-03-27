package com.github.Gymify.exception;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.DefaultGraphQLErrorHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GraphQLExceptionHandler extends DefaultGraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> list) {
        return list.stream()
            .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e) || e instanceof RuntimeExceptionWhileDataFetching)
            .map(e -> {
                if (e instanceof ExceptionWhileDataFetching) {
                    return new SanitizedError((ExceptionWhileDataFetching) e);
                } else if (e instanceof RuntimeExceptionWhileDataFetching) {
                    return new SanitizedError((RuntimeExceptionWhileDataFetching) e);
                } else {
                    return e;
                }
            })
            .collect(Collectors.toList());
    }
}
