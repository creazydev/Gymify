package com.github.Gymify.core.workout;

import com.github.Gymify.core.service.ScheduledWorkoutSessionService;
import com.github.Gymify.core.util.ObjectUtils;
import com.github.Gymify.persistence.entity.Period;
import com.github.Gymify.persistence.entity.ScheduledWorkoutSession;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.enums.WorkoutStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DefaultScheduleWorkoutSessionStrategy implements ScheduleWorkoutSessionStrategy {
    private final ScheduledWorkoutSessionService scheduledWorkoutSessionService;

    @Override
    @Transactional
    public List<ScheduledWorkoutSession> scheduleWorkoutSessions(WorkoutPlan workoutPlan) {
        LocalDate now = LocalDate.now();
        LocalDate scheduleDue = this.getScheduleDue();

        return workoutPlan
                .getWorkoutSessions()
                .parallelStream()
                .map(session -> this.getLocalDatesInPeriodByDayOfWeek(
                                now,
                                scheduleDue,
                                session.getDayOfWeek())
                        .parallelStream()
                        .map(date -> {
                            WorkoutSession sessionDeepCopy = session.deepCopy();

                            ScheduledWorkoutSession scheduledSession = new ScheduledWorkoutSession();
                            ObjectUtils.copyProperties(scheduledSession, sessionDeepCopy);
                            scheduledSession.setWorkoutStatus(WorkoutStatus.SCHEDULED);

                            LocalDateTime start = date.atTime(sessionDeepCopy.getStartTime());
                            scheduledSession.setPeriod(
                                    Period.of(
                                            Period.localDateTimeToTimestamp(start),
                                            Period.localDateTimeToTimestamp(start.plus(sessionDeepCopy.getDuration()))
                                    )
                            );
                            scheduledSession.setScheduledExercises(List.of());
                            return scheduledSession;
                        })
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .sequential()
                .map(scheduledWorkoutSessionService::add)
                .collect(Collectors.toList());
    }

    private LocalDate getScheduleDue() {
        return LocalDate.now()
                .plusMonths(3)
                .with(TemporalAdjusters.firstDayOfMonth());
    }
}
