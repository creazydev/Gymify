package com.github.Gymify.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.security.config.JwtConfiguration;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.filter;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JWTVerifier verifier;

    @Mock
    private Algorithm algorithm;

    private JwtService jwtService;
    private JwtService realSignJwtService;

    @BeforeEach
    void setUp() {
        this.jwtService = new JwtService(this.verifier, this.algorithm);

        JwtConfiguration jwtConfiguration = new JwtConfiguration();
        this.realSignJwtService = new JwtService(this.verifier, jwtConfiguration.jwtAlgorithm());
    }

    @Test
    void getDecodedToken_verificationFailed_decodedJwtEmpty() {
        doThrow(JWTVerificationException.class).when(this.verifier).verify(Strings.EMPTY);
        assertThat(this.jwtService.getDecodedToken(Strings.EMPTY)).isEmpty();
    }

    @Test
    void getDecodedToken_validJwt_decodedJwtNotEmpty() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdXBlckNvbXBhbnlNZW1iZXIiOiJmYWxzZSIsInN1YiI6InZzLmN2LjEzQGdtYWlsLmNvbSIsImFwaVRva2VucyI6Imh0dHA6Ly90ZXMyMy4wNXQuY29tIiwiY29tcGFueUlkIjoiNTEzIiwibmJmIjoxNjUzNDkwMDMyLCJleHAiOjE2NTM1NzY0MzIsInVzZXJJZCI6IjUxMSIsImF1dGhvcml0aWVzIjoiQURNSU4iLCJ1c2VybmFtZSI6InZzLmN2LjEzQGdtYWlsLmNvbSJ9.FOIethAwPAMS6IOf4O0ugLeLuRHSnFBYfcMoNmjMkJErj6mwHqMqfnFR7uvEiVzxhzm9owR_C5n822uJcaDeJItDnCIzQj-iSyFa58cpPFpO2E_gL87s3fGO5Q-e9lSiHg3Y3Bu-EwuA9T55a0ciXgFhSvKpjZM0eCpbwETDpqkVatT11ZY-s4cYhqVkEc5m9wMw7MdVQSy1BaRUWn-5XTLpSgeQmpXbUo8BvKdUdO0BZOXoprsy7MyxDJoYWTQyMJKLWoEF4aovYR3XQmZoDEg25vFpsEqg4xqBxyefgIMIcBzK4izfazCBPqGaRKxjK5yxI8Cal7lVcplk28UV0w";

        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        doReturn(decodedJWT).when(this.verifier).verify(jwt);
        assertThat(this.jwtService.getDecodedToken(jwt)).isNotEmpty();
    }

    @Test
    void getToken_userDetailsNull_throwException() {
        assertThrows(Exception.class, () -> this.jwtService.getToken(null));
    }

    @Test
    void getToken_validUserDetails_tokenNotBlank() {
        String email = "test@gmail.com";

        User user = new User();
        user.setEmail(email);
        String token = this.realSignJwtService.getToken(user);
        assertThat(token).isNotBlank();
    }

    @Test
    void getToken_validUserDetails_decodedEmailEquals() {
        String email = "test@gmail.com";

        User user = new User();
        user.setEmail(email);
        String token = this.realSignJwtService.getToken(user);

        DecodedJWT decodedJWT = JWT.decode(token);
        String subject = decodedJWT.getSubject();

        assertThat(email).isEqualTo(subject);
    }

    @Test
    void getToken_validUserDetails_tokenExpiresAtValid() {
        String email = "test@gmail.com";

        User user = new User();
        user.setEmail(email);

        final int secondError = 15;

        Instant before = Instant.now().plus(JwtConfiguration.EXPIRES_AT).minusSeconds(secondError);
        String token = this.realSignJwtService.getToken(user);
        Instant after = Instant.now().plus(JwtConfiguration.EXPIRES_AT).plusSeconds(secondError);

        DecodedJWT decodedJWT = JWT.decode(token);
        assertThat(decodedJWT.getExpiresAt()).isBetween(before, after);
    }
}
