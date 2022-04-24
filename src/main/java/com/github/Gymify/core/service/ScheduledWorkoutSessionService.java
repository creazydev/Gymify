package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.ScheduledWorkoutSession;
import com.github.Gymify.persistence.enums.WorkoutStatus;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.ScheduledWorkoutSessionSpecificationFactory;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledWorkoutSessionService extends UserResourceService<ScheduledWorkoutSession> {

    public ScheduledWorkoutSessionService(UserResourceRepository<ScheduledWorkoutSession> userResourceRepository, UserService userService, UserResourceSpecificationFactory<ScheduledWorkoutSession> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }

    public List<ScheduledWorkoutSession> findAllScheduled() {
        return this.findAll(getSpecificationFactory().statusEquals(WorkoutStatus.SCHEDULED));
    }

    @Override
    public ScheduledWorkoutSessionSpecificationFactory getSpecificationFactory() {
        return (ScheduledWorkoutSessionSpecificationFactory) super.getSpecificationFactory();
    }
}
