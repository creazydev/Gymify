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
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<UserDetails> findUserByToken(String token) {
        return this.jwtService
            .getDecodedToken(token)
            .map(DecodedJWT::getSubject)
            .map(this::loadUserByUsername);
    }

    public UserDetails loadUserByToken(String token) {
        return this.findUserByToken(token).orElseThrow(() -> RuntimeExceptionWhileDataFetching.notFound(User.class));
    }

    public Optional<User> findCurrentUser() {
        return Optional
            .ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getName)
            .flatMap(userRepository::findByEmail);
    }

    public User getCurrentUser() {
        return this.findCurrentUser().orElseThrow(RuntimeExceptionWhileDataFetching::unAuthorized);
    }
}
