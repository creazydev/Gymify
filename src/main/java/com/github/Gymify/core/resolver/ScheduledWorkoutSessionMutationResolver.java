package com.github.Gymify.core.resolver;

import com.github.Gymify.core.service.ScheduledWorkoutSessionService;
import com.github.Gymify.core.workout.ScheduledWorkoutSessionAggregator;
import com.github.Gymify.persistence.entity.ScheduledWorkoutSession;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledWorkoutSessionMutationResolver implements GraphQLMutationResolver {
    private final ScheduledWorkoutSessionService scheduledWorkoutSessionService;
    private final ScheduledWorkoutSessionAggregator scheduledWorkoutSessionAggregator;

    public ScheduledWorkoutSession updateScheduledWorkoutSession(ScheduledWorkoutSession scheduledWorkoutSession) {
        return this.scheduledWorkoutSessionService.update(scheduledWorkoutSession);
    }

    public ScheduledWorkoutSession startSession(Long id) {
        ScheduledWorkoutSession session = this.scheduledWorkoutSessionService.getOrThrowNotFound(id);
        return this.scheduledWorkoutSessionAggregator.startSession(session);
    }

    public ScheduledWorkoutSession completeSession(Long id) {
        ScheduledWorkoutSession session = this.scheduledWorkoutSessionService.getOrThrowNotFound(id);
        return this.scheduledWorkoutSessionAggregator.completeSession(session);
    }

    public ScheduledWorkoutSession cancelSession(Long id) {
        ScheduledWorkoutSession session = this.scheduledWorkoutSessionService.getOrThrowNotFound(id);
        return this.scheduledWorkoutSessionAggregator.cancel(session);
    }
}
