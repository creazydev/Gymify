package com.github.Gymify.core.resolver;

import com.github.Gymify.core.service.WorkoutPlanService;
import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class WorkoutPlanMutationResolver implements GraphQLMutationResolver {
    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlan addWorkoutPlan(WorkoutPlan workoutPlan) {
        return this.workoutPlanService.add(workoutPlan);
    }

    public WorkoutPlan updateWorkoutPlan(WorkoutPlan workoutPlan) {
        return this.workoutPlanService.update(workoutPlan);
    }

    public WorkoutPlan activateWorkoutPlan(Long workoutPlanId) {
        return this.workoutPlanService.find(workoutPlanId)
                .map(this.workoutPlanService::activate)
                .orElseThrow(() -> RuntimeExceptionWhileDataFetching.notFound(WorkoutPlan.class));
    }

    public void deleteWorkoutPlan(Long id) {
        this.workoutPlanService.delete(id);
    }
}
