package com.github.Gymify.core.resolver;

import com.github.Gymify.core.dto.PageableRequest;
import com.github.Gymify.core.filter.GraphQLFilterChain;
import com.github.Gymify.core.filter.GraphQLSpecificationFilter;
import com.github.Gymify.core.service.WorkoutService;
import com.github.Gymify.persistence.entity.Workout;
import com.github.Gymify.security.service.UserService;
import com.github.Gymify.user.model.AuthenticatedUser;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final UserService userService;
    private final WorkoutService workoutService;

    public AuthenticatedUser getCurrentUser() {
        return new AuthenticatedUser(userService.getCurrentUser(), null);
    }

    public Page<Workout> getCurrentUserWorkouts(PageableRequest pageableRequest, GraphQLFilterChain filter) {
        return this.workoutService.getAllByCurrentUserId(
            pageableRequest.getPageable(),
            new GraphQLSpecificationFilter<Workout>(filter).getSpecification()
        );
    }
}
