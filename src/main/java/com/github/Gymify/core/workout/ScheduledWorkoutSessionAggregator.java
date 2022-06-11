package com.github.Gymify.core.workout;

import com.github.Gymify.core.service.ScheduledWorkoutSessionService;
import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.ScheduledWorkoutSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.github.Gymify.persistence.enums.WorkoutStatus.*;

@Component
@Transactional
@RequiredArgsConstructor
public class ScheduledWorkoutSessionAggregator {

    @Getter
    private final ScheduleWorkoutSessionStrategy scheduleWorkoutSessionStrategy;
    private final ScheduledWorkoutSessionService scheduledWorkoutSessionService;

    public ScheduledWorkoutSession startSession(ScheduledWorkoutSession scheduledWorkoutSession) {
        return Optional.ofNullable(scheduledWorkoutSession)
                .filter(session -> Objects.equals(session.getWorkoutStatus(), SCHEDULED))
                .stream()
                .peek(session -> session.setWorkoutStatus(ON_GOING))
                .map(scheduledWorkoutSessionService::update)
                .findFirst()
                .orElseThrow(() -> new RuntimeExceptionWhileDataFetching(
                        HttpStatus.BAD_REQUEST,
                        "Could not start scheduled workout session!"
                ));
    }

    public ScheduledWorkoutSession completeSession(ScheduledWorkoutSession scheduledWorkoutSession) {
        return Optional.ofNullable(scheduledWorkoutSession)
                .filter(session -> Objects.equals(session.getWorkoutStatus(), ON_GOING))
                .stream()
                .peek(session -> session.setWorkoutStatus(COMPLETED))
                .map(scheduledWorkoutSessionService::update)
                .findAny()
                .orElseThrow(() -> new RuntimeExceptionWhileDataFetching(
                        HttpStatus.BAD_REQUEST,
                        "Could not complete scheduled workout session!"
                ));
    }

    public void cancelScheduled() {
        this.cancelAll(this.scheduledWorkoutSessionService.getSpecificationFactory().workoutStatusEquals(SCHEDULED));
    }

    public void deleteScheduled() {
        this.scheduledWorkoutSessionService
            .findAll(this.scheduledWorkoutSessionService.getSpecificationFactory().workoutStatusEquals(SCHEDULED))
            .stream()
            .sequential()
            .forEachOrdered(el -> this.scheduledWorkoutSessionService.delete(el.getId()));
    }

    public void cancelAll(Specification<ScheduledWorkoutSession> specification) {
        this.scheduledWorkoutSessionService
                .findAll(specification)
                .stream()
                .sequential()
                .forEachOrdered(this::cancel);
    }

    public ScheduledWorkoutSession cancel(ScheduledWorkoutSession scheduledWorkoutSession) {
        return Optional.ofNullable(scheduledWorkoutSession)
                .filter(session -> Set.of(ON_GOING, SCHEDULED).contains(session.getWorkoutStatus()))
                .stream()
                .peek(session -> session.setWorkoutStatus(CANCELED))
                .map(scheduledWorkoutSessionService::update)
                .findFirst()
                .orElseThrow(() -> new RuntimeExceptionWhileDataFetching(
                        HttpStatus.BAD_REQUEST,
                        "Could not cancel scheduled workout session!"
                ));
    }
}
