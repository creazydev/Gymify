package com.github.Gymify.configuration;

import graphql.language.IntValue;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.GregorianCalendar;

@Configuration
public class GraphQLConfiguration {

    @Bean
    public GraphQLScalarType dateTime() {
        return ExtendedScalars.DateTime;
    }

    @Bean
    public GraphQLScalarType graphQLLong() {
        return ExtendedScalars.GraphQLLong;
    }

    @Bean
    public GraphQLScalarType timestamp() {
        return new GraphQLScalarType.Builder()
            .name("Timestamp")
            .description("Timestamp")
            .coercing(new Coercing<Object, Object>() {
                @Override
                public Object serialize(Object input) {
                    return input;
                }

                @Override
                public Object parseValue(Object input) {
                    GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(((IntValue)input).getValue().multiply(new BigInteger("1000")).longValue());
                    return calendar;
                }

                @Override
                public Object parseLiteral(Object input) {
                    GregorianCalendar calendar = new GregorianCalendar();
                    calendar.setTimeInMillis(((IntValue)input).getValue().multiply(new BigInteger("1000")).longValue());

                    return calendar;
                }
            })
            .build();
    }
}
