package com.github.Gymify.persistence.entity;

import com.github.Gymify.persistence.enums.WorkoutStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class ScheduledExercise extends Exercise {

    @Enumerated
    @Column(name = "workout_status", nullable = false)
    private WorkoutStatus workoutStatus;

    @OneToMany(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "scheduled_exercise_id", referencedColumnName = "id")
    private List<ScheduledSet> scheduledSets;

    @Column(name = "actual_rest_duration")
    private Integer actualRestDuration;
}
