package com.github.Gymify.core.resolver;

import com.github.Gymify.security.service.UserService;
import com.github.Gymify.user.model.AuthenticatedUser;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final UserService userService;

    public AuthenticatedUser getCurrentUser() {
        return new AuthenticatedUser(userService.getCurrentUser(), null);
    }
}
