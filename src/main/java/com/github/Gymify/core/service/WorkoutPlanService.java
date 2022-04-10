package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class WorkoutPlanService extends UserResourceService<WorkoutPlan> {

    public WorkoutPlanService(UserResourceRepository<WorkoutPlan> userResourceRepository, UserService userService, UserResourceSpecificationFactory<WorkoutPlan> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }
}
