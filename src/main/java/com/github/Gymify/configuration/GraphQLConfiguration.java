package com.github.Gymify.configuration;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfiguration {

    @Bean
    public GraphQLScalarType dateTime() {
        return ExtendedScalars.DateTime;
    }
}
