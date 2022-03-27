package com.github.Gymify.user.resolver;

import com.github.Gymify.configuration.GLogger;
import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.user.model.AuthenticatedUser;
import com.github.Gymify.security.service.JwtService;
import com.github.Gymify.security.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationResolver implements GraphQLMutationResolver, GLogger {
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final JwtService jwtService;

    @PreAuthorize("isAnonymous()")
    public AuthenticatedUser login(String email, String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder
                .getContext()
                .setAuthentication(authenticationProvider.authenticate(credentials));

            UserDetails currentUser = userService.getCurrentUser();
            return new AuthenticatedUser(
                currentUser,
                jwtService.getToken(currentUser)
            );
        } catch (BadCredentialsException ex) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (AuthenticationException ex) {
            throw RuntimeExceptionWhileDataFetching.unAuthorized();
        }
    }
}
