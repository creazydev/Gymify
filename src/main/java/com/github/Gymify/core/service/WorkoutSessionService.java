package com.github.Gymify.core.service;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.persistence.specification.WorkoutSessionSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

@Service
public class WorkoutSessionService extends UserResourceService<WorkoutSession> implements PrePersistEntityValidator<WorkoutSession> {
    private final UserResourceListParentDelegate<Exercise> exerciseListParentDelegate;

    public WorkoutSessionService(UserResourceRepository<WorkoutSession> userResourceRepository, UserService userService, UserResourceSpecificationFactory<WorkoutSession> specificationFactory, UserResourceListParentDelegate<Exercise> exerciseListParentDelegate) {
        super(userResourceRepository, userService, specificationFactory);
        this.exerciseListParentDelegate = exerciseListParentDelegate;
    }

    @Override
    public WorkoutSession add(WorkoutSession obj) {
        this.validateEntity(obj);
        obj.setExercises(this.exerciseListParentDelegate.updateCollection(Collections.emptyList(), obj.getExercises()));
        return super.add(obj);
    }

    @Override
    public WorkoutSession update(WorkoutSession workoutSession) {
        return this.find(workoutSession.getId())
            .stream()
            .peek(savedWorkoutPlan -> {
                savedWorkoutPlan.setDuration(workoutSession.getDuration());
                savedWorkoutPlan.setDayOfWeek(workoutSession.getDayOfWeek());
                savedWorkoutPlan.setStartTime(workoutSession.getStartTime());
                savedWorkoutPlan.setExercises(
                    this.exerciseListParentDelegate.updateCollection(
                        savedWorkoutPlan.getExercises(), workoutSession.getExercises()
                    )
                );
            })
            .peek(this::validateEntity)
            .map(super::update)
            .findFirst()
            .orElseGet(() -> this.add(workoutSession));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.find(id)
            .ifPresent(plan -> {
                this.exerciseListParentDelegate.updateCollection(plan.getExercises(), Collections.emptyList());
                super.delete(id);
            });
    }

    @Override
    public void validateEntity(WorkoutSession obj) {
        if (Objects.isNull(obj)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Workout session cannot be null!");
        } else if (Objects.isNull(obj.getDuration())) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Duration of workout session cannot be null!");
        } else if (Objects.isNull(obj.getExercises())) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Duration session exercises cannot be null!");
        } else if (Objects.isNull(obj.getDayOfWeek())) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Day of week of workout session cannot be null!");
        } else if (Objects.isNull(obj.getStartTime())) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Start time of workout session cannot be null!");
        }
    }

    @Override
    protected WorkoutSessionSpecificationFactory getSpecificationFactory() {
        return (WorkoutSessionSpecificationFactory) super.getSpecificationFactory();
    }
}
