package com.github.Gymify.core.service;

import com.github.Gymify.core.workout.ScheduledWorkoutSessionAggregator;
import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.repository.WorkoutPlanRepository;
import com.github.Gymify.persistence.specification.WorkoutPlanSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutPlanService extends UserResourceService<WorkoutPlan> implements PrePersistEntityValidator<WorkoutPlan> {
    private final ScheduledWorkoutSessionService scheduledWorkoutSessionService;
    private final ScheduledWorkoutSessionAggregator scheduledWorkoutSessionAggregator;

    private final UserResourceListParentDelegate<WorkoutSession> workoutSessionListParentDelegate;
    private final WorkoutSessionService workoutSessionService;
    private final PrePersistEntityValidator<WorkoutSession> workoutSessionPrePersistEntityValidator;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository, UserService userService,
                              WorkoutPlanSpecificationFactory workoutPlanSpecificationFactory,
                              ScheduledWorkoutSessionService scheduledWorkoutSessionService, ScheduledWorkoutSessionAggregator scheduledWorkoutSessionAggregator, UserResourceListParentDelegate<WorkoutSession> workoutSessionListParentDelegate, WorkoutSessionService workoutSessionService, PrePersistEntityValidator<WorkoutSession> workoutSessionPrePersistEntityValidator) {
        super(workoutPlanRepository, userService, workoutPlanSpecificationFactory);
        this.scheduledWorkoutSessionService = scheduledWorkoutSessionService;
        this.scheduledWorkoutSessionAggregator = scheduledWorkoutSessionAggregator;
        this.workoutSessionListParentDelegate = workoutSessionListParentDelegate;
        this.workoutSessionService = workoutSessionService;
        this.workoutSessionPrePersistEntityValidator = workoutSessionPrePersistEntityValidator;
    }

    @Override
    @Transactional
    public WorkoutPlan add(WorkoutPlan workoutPlan) {
        workoutPlan.setActive(false);

        if (Optional.ofNullable(workoutPlan.getWorkoutSessions()).filter(list -> !list.isEmpty()).isPresent()) {
            workoutPlan.setWorkoutSessions(
                this.workoutSessionListParentDelegate.updateCollection(
                    List.of(), workoutPlan.getWorkoutSessions()
                )
            );
        } else {
            workoutPlan.setWorkoutSessions(List.of());
        }

        this.validateEntity(workoutPlan);
        return super.add(workoutPlan);
    }

    @Override
    @Transactional
    public WorkoutPlan update(WorkoutPlan workoutPlan) {
        WorkoutPlan savedWorkoutPlan = this.find(workoutPlan.getId())
            .orElseGet(() -> this.add(workoutPlan));

        savedWorkoutPlan.setName(workoutPlan.getName());
        savedWorkoutPlan.setWorkoutSessions(
            this.workoutSessionListParentDelegate.updateCollection(
                workoutPlan.getWorkoutSessions(), savedWorkoutPlan.getWorkoutSessions()
            )
        );

        this.validateEntity(savedWorkoutPlan);
        return super.update(savedWorkoutPlan);
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
            Specification<WorkoutPlan> currentUserActiveWorkoutPlanExcludingIdSpec = this.getSpecificationFactory().active()
                .and(this.getSpecificationFactory().idNotEquals(workoutPlan.getId()))
                .and(this.currentUserSpecification());

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
        final boolean wasActive = this.find(workoutPlan.getId())
            .filter(WorkoutPlan::getActive)
            .isPresent();

        if (!wasActive) {
            this.scheduledWorkoutSessionAggregator.deleteScheduled();
            workoutPlan.setActive(true);
            this.update(workoutPlan);
            this.scheduledWorkoutSessionAggregator
                .getScheduleWorkoutSessionStrategy()
                .scheduleWorkoutSessions(workoutPlan);
        }

        return workoutPlan;
    }
}
