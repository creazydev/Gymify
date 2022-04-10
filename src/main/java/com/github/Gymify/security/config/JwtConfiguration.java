package com.github.Gymify.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.Gymify.security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class JwtConfiguration {

    @Bean
    public Algorithm jwtAlgorithm() {
        return Algorithm.HMAC256("my-JWT-secret");
    }

    @Bean
    public JWTVerifier verifier(Algorithm algorithm) {
        return JWT
            .require(algorithm)
            .withIssuer("my-graphql-api")
            .build();
    }
}
