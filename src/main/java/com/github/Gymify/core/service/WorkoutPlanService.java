package com.github.Gymify.core.service;

import com.github.Gymify.core.workout.ScheduledWorkoutSessionAggregator;
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
    private final ScheduledWorkoutSessionService scheduledWorkoutSessionService;
    private final ScheduledWorkoutSessionAggregator scheduledWorkoutSessionAggregator;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository, UserService userService,
                              WorkoutPlanSpecificationFactory workoutPlanSpecificationFactory,
                              ScheduledWorkoutSessionService scheduledWorkoutSessionService, ScheduledWorkoutSessionAggregator scheduledWorkoutSessionAggregator) {
        super(workoutPlanRepository, userService, workoutPlanSpecificationFactory);
        this.scheduledWorkoutSessionService = scheduledWorkoutSessionService;
        this.scheduledWorkoutSessionAggregator = scheduledWorkoutSessionAggregator;
    }

    @Override
    @Transactional
    public WorkoutPlan add(WorkoutPlan workoutPlan) {
        this.validateEntity(workoutPlan);
        return super.add(workoutPlan);
    }

    @Override
    @Transactional
    public WorkoutPlan update(WorkoutPlan workoutPlan) {
        this.validateEntity(workoutPlan);

        if (workoutPlan.getActive()) {
            this.scheduledWorkoutSessionAggregator.cancelScheduled();
            this.scheduledWorkoutSessionAggregator
                    .getScheduleWorkoutSessionStrategy()
                    .scheduleWorkoutSessions(workoutPlan);
        }

        return super.update(workoutPlan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.find(id)
                .ifPresent(plan -> {
                    if (plan.getActive()) {
                        this.scheduledWorkoutSessionAggregator.cancelScheduled();
                    }
                    super.delete(id);
                });
    }

    @Override
    public void validateEntity(WorkoutPlan workoutPlan) {
        if (workoutPlan.getActive()) {
            Specification<WorkoutPlan> currentUserActiveWorkoutPlanExcludingIdSpec = getSpecificationFactory().active()
                    .and(this.getSpecificationFactory().idNotEquals(workoutPlan.getId()))
                    .and(currentUserSpecification());

            if (this.getUserResourceRepository()
                    .findOne(currentUserActiveWorkoutPlanExcludingIdSpec)
                    .isPresent()) {
                throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "There is already active workout plan!");
            }
        }
    }

    @Override
    protected WorkoutPlanSpecificationFactory getSpecificationFactory() {
        return (WorkoutPlanSpecificationFactory) super.getSpecificationFactory();
    }

    @Transactional
    public WorkoutPlan activate(WorkoutPlan workoutPlan) {
        if (!workoutPlan.getActive()) {
            this.scheduledWorkoutSessionAggregator.cancelScheduled();
            this.scheduledWorkoutSessionAggregator
                    .getScheduleWorkoutSessionStrategy()
                    .scheduleWorkoutSessions(workoutPlan);
            workoutPlan.setActive(true);
            this.update(workoutPlan);
        }

        return workoutPlan;
    }
}
