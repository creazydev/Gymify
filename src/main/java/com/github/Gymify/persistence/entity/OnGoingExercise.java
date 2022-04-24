package com.github.Gymify.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@NoArgsConstructor
@Getter
@Setter
public class OnGoingExercise extends Exercise {

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "completed_exercise_id", referencedColumnName = "id")
    private List<CompletedSet> completedSets;

    @OneToOne
    @JoinColumn(name = "scheduled_workout_sesson_id", referencedColumnName = "id")
    private ScheduledWorkoutSession scheduledWorkoutSession;
}
