package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.ScheduledExercise;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ScheduledExerciseService extends UserResourceService<ScheduledExercise> {

    public ScheduledExerciseService(UserResourceRepository<ScheduledExercise> userResourceRepository, UserService userService, UserResourceSpecificationFactory<ScheduledExercise> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }
}
