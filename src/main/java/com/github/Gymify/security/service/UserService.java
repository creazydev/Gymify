package com.github.Gymify.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserDetails loadUserByToken(String token) {
        return jwtService.getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .map(this::loadUserByUsername)
                .orElseThrow(() -> RuntimeExceptionWhileDataFetching.notFound(User.class));
    }

    public UserDetails getCurrentUser() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(userRepository::findByEmail)
                .orElseThrow(RuntimeExceptionWhileDataFetching::unAuthorized);
    }
}
