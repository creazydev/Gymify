package com.github.Gymify.core.workout;

import com.github.Gymify.core.service.CompletedExerciseService;
import com.github.Gymify.core.service.OnGoingExerciseService;
import com.github.Gymify.core.service.ScheduledWorkoutSessionService;
import com.github.Gymify.core.util.ObjectUtils;
import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.*;
import com.github.Gymify.persistence.enums.WorkoutStatus;
import com.github.Gymify.persistence.specification.ScheduledWorkoutSessionSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class ScheduledExerciseAggregator {
    private final ScheduledWorkoutSessionService scheduledWorkoutSessionService;
    private final OnGoingExerciseService onGoingExerciseService;
    private final CompletedExerciseService completedExerciseService;
    private final ScheduledWorkoutSessionSpecificationFactory scheduledWorkoutSessionSpecificationFactory;

    public OnGoingExercise startExercise(Exercise exercise) {
        Specification<ScheduledWorkoutSession> specification = scheduledWorkoutSessionSpecificationFactory
                .exerciseIdIn(exercise.getId())
                .and(scheduledWorkoutSessionSpecificationFactory.statusEquals(WorkoutStatus.ON_GOING));

        ScheduledWorkoutSession session = this.scheduledWorkoutSessionService.find(specification)
                .orElseThrow(() -> RuntimeExceptionWhileDataFetching.notFound(ScheduledWorkoutSession.class));

        OnGoingExercise onGoingExercise = new OnGoingExercise();
        ObjectUtils.copyProperties(onGoingExercise, exercise);
        onGoingExercise.setSets(
                exercise
                        .getSets()
                        .stream()
                        .sequential()
                        .map(Set::deepCopy)
                        .collect(Collectors.toList())
        );
        onGoingExercise.setCompletedSets(List.of());
        onGoingExercise.setScheduledWorkoutSession(session);
        return this.onGoingExerciseService.add(onGoingExercise);
    }

    public CompletedExercise completeScheduledExercise(OnGoingExercise onGoingExercise, Integer actualRestDuration) {
        ScheduledWorkoutSession session = onGoingExercise.getScheduledWorkoutSession();

        CompletedExercise completedExercise = new CompletedExercise();
        ObjectUtils.copyProperties(completedExercise, onGoingExercise);
        completedExercise.setActualRestDuration(actualRestDuration);
        completedExercise = this.completedExerciseService.add(completedExercise);
        session.getCompletedExercises().add(completedExercise);

        this.onGoingExerciseService.delete(onGoingExercise.getId());
        return completedExercise;
    }
}
