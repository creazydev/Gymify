package com.github.Gymify.core.workout;

import com.github.Gymify.core.service.ScheduledExerciseService;
import com.github.Gymify.core.service.ScheduledWorkoutSessionService;
import com.github.Gymify.core.util.ObjectUtils;
import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.*;
import com.github.Gymify.persistence.enums.WorkoutStatus;
import com.github.Gymify.persistence.specification.ScheduledWorkoutSessionSpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class ScheduledExerciseAggregator {
    private final ScheduledWorkoutSessionService scheduledWorkoutSessionService;
    private final ScheduledExerciseService scheduledExerciseService;
    private final ScheduledWorkoutSessionSpecificationFactory scheduledWorkoutSessionSpecificationFactory;

    public ScheduledExercise startExercise(Exercise exercise) {
        Specification<ScheduledWorkoutSession> specification = scheduledWorkoutSessionSpecificationFactory
                .exerciseIdIn(exercise.getId())
                .and(scheduledWorkoutSessionSpecificationFactory.workoutStatusEquals(WorkoutStatus.ON_GOING));

        ScheduledWorkoutSession session = this.scheduledWorkoutSessionService.find(specification)
                .orElseThrow(() -> RuntimeExceptionWhileDataFetching.notFound(ScheduledWorkoutSession.class));

        ScheduledExercise scheduledExercise = new ScheduledExercise();
        ObjectUtils.copyProperties(scheduledExercise, exercise);
        scheduledExercise.setSets(
                exercise
                        .getSets()
                        .stream()
                        .sequential()
                        .map(Set::deepCopy)
                        .collect(Collectors.toList())
        );
        scheduledExercise.setScheduledSets(List.of());
        return this.scheduledExerciseService.add(scheduledExercise);
    }

    public ScheduledExercise completeScheduledExercise(ScheduledExercise scheduledExercise, Integer actualRestDuration) {
        throw new NotImplementedException();

//        ScheduledWorkoutSession session = scheduledExercise.getScheduledWorkoutSession();
//
//        CompletedExercise completedExercise = new CompletedExercise();
//        ObjectUtils.copyProperties(completedExercise, onGoingExercise);
//        completedExercise.setActualRestDuration(actualRestDuration);
//        completedExercise = this.completedExerciseService.add(completedExercise);
//        session.getCompletedExercises().add(completedExercise);
//
//        this.onGoingExerciseService.delete(onGoingExercise.getId());
//        return completedExercise;
    }
}
