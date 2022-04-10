package com.github.Gymify.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class WorkoutSession extends UserResource {

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
}
