package com.github.Gymify.core.workout;

import com.github.Gymify.persistence.entity.ScheduledWorkoutSession;
import com.github.Gymify.persistence.entity.WorkoutPlan;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ScheduleWorkoutSessionStrategy {
    List<ScheduledWorkoutSession> scheduleWorkoutSessions(WorkoutPlan workoutPlan);

    default Collection<LocalDate> getLocalDatesInPeriodByDayOfWeek(LocalDate start, LocalDate inclusiveEnd, DayOfWeek dayOfWeek) {
        return Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, inclusiveEnd))
                .filter(date -> Objects.equals(date.getDayOfWeek(), dayOfWeek))
                .collect(Collectors.toList());
    }
}
