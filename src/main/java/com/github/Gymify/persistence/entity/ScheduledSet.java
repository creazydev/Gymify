package com.github.Gymify.persistence.entity;

import com.github.Gymify.persistence.enums.WorkoutStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class ScheduledSet extends Set {

    @Enumerated
    @Column(name = "workout_status", nullable = false)
    private WorkoutStatus workoutStatus;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "actual_rest_duration", nullable = false)
    private Integer actualRestDuration;
}
