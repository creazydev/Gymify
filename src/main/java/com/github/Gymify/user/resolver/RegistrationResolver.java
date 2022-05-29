package com.github.Gymify.user.resolver;

import com.github.Gymify.configuration.GLogger;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.security.service.JwtService;
import com.github.Gymify.user.RegistrationService;
import com.github.Gymify.user.model.AuthenticatedUser;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RegistrationResolver implements GraphQLMutationResolver, GLogger {
    private final RegistrationService registrationService;
    private final JwtService jwtService;

    @PreAuthorize("isAnonymous()")
    public AuthenticatedUser register(String email, String password) {
        User user = User.builder()
                .email(email)
                .password(password)
                .authorities(Set.of(UserAuthority.BASIC_USER))
                .build();

        user = this.registrationService.register(user);
        return new AuthenticatedUser(user, this.jwtService.getToken(user));
    }
}
