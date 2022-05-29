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
public class ScheduledWorkoutSession extends WorkoutSession {

    @Enumerated
    @Column(name = "workout_status", nullable = false)
    private WorkoutStatus workoutStatus;

    @Column(name = "period", nullable = false)
    private Period period;

    @OneToMany
    @JoinColumn(name = "scheduled_exercise_id", referencedColumnName = "id", nullable = false)
    private List<ScheduledExercise> scheduledExercises;
}
