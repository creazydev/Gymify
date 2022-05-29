package com.github.Gymify.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class WorkoutPlan extends UserResource {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinColumn(name = "workout_plan_id", referencedColumnName = "id")
    private List<WorkoutSession> workoutSessions;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Builder
    public WorkoutPlan(String name, List<WorkoutSession> workoutSessions, Boolean active, User user) {
        this.name = name;
        this.workoutSessions = workoutSessions;
        this.active = active;
        this.user = user;
    }
}
