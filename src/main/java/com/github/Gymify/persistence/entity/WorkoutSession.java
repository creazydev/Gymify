package com.github.Gymify.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@NoArgsConstructor
@Getter
@Setter
public class WorkoutSession extends UserResource implements DeepCopyEntity {

    @Enumerated
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "duration", nullable = false)
    private Duration duration;

    @ManyToMany
    @JoinColumn(name = "workout_session_id", referencedColumnName = "id")
    private List<Exercise> exercises;

    @Override
    public WorkoutSession deepCopy() {
        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.dayOfWeek = this.dayOfWeek;
        workoutSession.duration = this.duration;
        workoutSession.startTime = this.startTime;
        workoutSession.user = this.user;
        workoutSession.exercises = this.exercises.stream()
                .map(Exercise::deepCopy)
                .collect(Collectors.toList());

        return workoutSession;
    }
}
