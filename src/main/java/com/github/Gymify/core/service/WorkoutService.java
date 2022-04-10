package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.Workout;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService extends UserResourceService<Workout> {

    public WorkoutService(UserResourceRepository<Workout> userResourceRepository, UserService userService, UserResourceSpecificationFactory<Workout> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }
}
