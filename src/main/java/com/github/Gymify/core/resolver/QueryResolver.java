package com.github.Gymify.core.resolver;

import com.github.Gymify.core.dto.PageableRequest;
import com.github.Gymify.core.filter.GraphQLFilterChain;
import com.github.Gymify.core.filter.GraphQLSpecificationFilter;
import com.github.Gymify.core.service.WorkoutPlanService;
import com.github.Gymify.core.service.WorkoutSessionService;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
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
    private final WorkoutSessionService workoutSessionService;
    private final WorkoutPlanService workoutPlanService;

    public AuthenticatedUser getCurrentUser() {
        return new AuthenticatedUser(userService.getCurrentUser(), null);
    }

    public Page<WorkoutSession> getCurrentUserWorkoutSessions(PageableRequest pageableRequest, GraphQLFilterChain filter) {
        return this.workoutSessionService.getAllByCurrentUserId(
            pageableRequest.getPageable(),
            new GraphQLSpecificationFilter<WorkoutSession>(filter).getSpecification()
        );
    }

    public Page<WorkoutPlan> getCurrentUserWorkoutPlans(PageableRequest pageableRequest, GraphQLFilterChain filter) {
        return this.workoutPlanService.getAllByCurrentUserId(
            pageableRequest.getPageable(),
            new GraphQLSpecificationFilter<WorkoutPlan>(filter).getSpecification()
        );
    }

    public WorkoutPlan getWorkoutPlanById(Long id) {
        return this.workoutPlanService.getOrThrowNotFound(id);
    }
}
