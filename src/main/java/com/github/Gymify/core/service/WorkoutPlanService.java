package com.github.Gymify.core.service;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.repository.WorkoutPlanRepository;
import com.github.Gymify.persistence.specification.WorkoutPlanSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkoutPlanService extends UserResourceService<WorkoutPlan> implements PrePersistEntityValidator<WorkoutPlan> {

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository, UserService userService,
                              WorkoutPlanSpecificationFactory workoutPlanSpecificationFactory) {
        super(workoutPlanRepository, userService, workoutPlanSpecificationFactory);
    }

    @Override
    @Transactional
    public WorkoutPlan add(WorkoutPlan workoutPlan) {
        return super.add(workoutPlan);
    }

    @Override
    public void validateEntity(WorkoutPlan workoutPlan) {
        Specification<WorkoutPlan> currentUserActiveWorkoutPlanExcludingIdSpec = getSpecificationFactory().active()
            .and(this.getSpecificationFactory().idNotEquals(workoutPlan.getId()))
            .and(currentUserSpecification());

        if (workoutPlan.getActive() && this.getUserResourceRepository()
            .findOne(currentUserActiveWorkoutPlanExcludingIdSpec)
            .isPresent()) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "There is already active workout plan!");
        }
    }

    @Override
    protected WorkoutPlanSpecificationFactory getSpecificationFactory() {
        return (WorkoutPlanSpecificationFactory) super.getSpecificationFactory();
    }
}
