package com.github.Gymify.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.github.Gymify.security.config.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JWTVerifier verifier;
    private final Algorithm algorithm;

    public Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch(JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public String getToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(JwtConfiguration.EXPIRES_AT);
        return JWT
                .create()
                .withIssuer("my-graphql-api")
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }
}
