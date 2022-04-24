package com.github.Gymify.core.resolver;

import com.github.Gymify.core.service.ExerciseService;
import com.github.Gymify.core.service.OnGoingExerciseService;
import com.github.Gymify.core.workout.ScheduledExerciseAggregator;
import com.github.Gymify.persistence.entity.CompletedExercise;
import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.OnGoingExercise;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExerciseMutationResolver implements GraphQLMutationResolver {
    private final ScheduledExerciseAggregator scheduledExerciseAggregator;
    private final ExerciseService exerciseService;
    private final OnGoingExerciseService onGoingExerciseService;

    public OnGoingExercise startExercise(Long exerciseId) {
        Exercise exercise = this.exerciseService.getOrThrowNotFound(exerciseId);
        return this.scheduledExerciseAggregator.startExercise(exercise);
    }

    public CompletedExercise completeExercise(Long onGoingExerciseId, Integer actualRestDuration) {
        OnGoingExercise onGoingExercise = this.onGoingExerciseService.getOrThrowNotFound(onGoingExerciseId);
        return this.scheduledExerciseAggregator.completeScheduledExercise(onGoingExercise, actualRestDuration);
    }
}
