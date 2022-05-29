package com.github.Gymify.core.resolver;

import com.github.Gymify.core.service.ExerciseService;
import com.github.Gymify.core.service.ScheduledExerciseService;
import com.github.Gymify.core.workout.ScheduledExerciseAggregator;
import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.ScheduledExercise;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExerciseMutationResolver implements GraphQLMutationResolver {
    private final ScheduledExerciseAggregator scheduledExerciseAggregator;
    private final ExerciseService exerciseService;
    private final ScheduledExerciseService scheduledExerciseService;

    public ScheduledExercise startScheduledExercise(Long scheduledExerciseId) {
        Exercise exercise = this.exerciseService.getOrThrowNotFound(scheduledExerciseId);
        return this.scheduledExerciseAggregator.startExercise(exercise);
    }

    public ScheduledExercise completeScheduledExercise(Long scheduledExerciseId, Integer actualRestDuration) {
        ScheduledExercise scheduledExercise = this.scheduledExerciseService.getOrThrowNotFound(scheduledExerciseId);
        return this.scheduledExerciseAggregator.completeScheduledExercise(scheduledExercise, actualRestDuration);
    }
}
