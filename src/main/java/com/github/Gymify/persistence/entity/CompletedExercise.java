package com.github.Gymify.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class CompletedExercise extends Exercise {

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "completed_exercise_id", referencedColumnName = "id")
    private List<CompletedSet> completedSets;

    @Column(name = "actual_rest_duration")
    private Integer actualRestDuration;
}
