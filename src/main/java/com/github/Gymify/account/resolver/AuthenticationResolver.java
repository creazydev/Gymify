package com.github.Gymify.account.resolver;

import com.github.Gymify.account.model.AuthenticatedUser;
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
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class AuthenticationResolver implements GraphQLMutationResolver {
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
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }
}
