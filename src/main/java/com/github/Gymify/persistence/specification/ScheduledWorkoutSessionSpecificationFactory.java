package com.github.Gymify.persistence.specification;

import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.ScheduledWorkoutSession;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.enums.WorkoutStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;

@Component
public class ScheduledWorkoutSessionSpecificationFactory extends UserResourceSpecificationFactory<ScheduledWorkoutSession> {

    public Specification<ScheduledWorkoutSession> workoutStatusEquals(WorkoutStatus status) {
        return (root, cq, cb) -> cb.equal(root.get("workoutStatus"), status);
    }

    public Specification<ScheduledWorkoutSession> exerciseIdIn(Long exerciseId) {
        return (root, cq, cb) -> {
            Join<ScheduledWorkoutSession, Exercise> exercise = root.join("exercises");
            return cb.equal(exercise.get("id"), exerciseId);
        };
    }

    public Specification<ScheduledWorkoutSession> completedExerciseIdIn(Long completedExerciseId) {
        return (root, cq, cb) -> {
            Join<ScheduledWorkoutSession, Exercise> exercise = root.join("completedExercises");
            return cb.equal(exercise.get("id"), completedExerciseId);
        };
    }
}
