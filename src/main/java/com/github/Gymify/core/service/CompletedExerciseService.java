package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.CompletedExercise;
import com.github.Gymify.persistence.entity.OnGoingExercise;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CompletedExerciseService extends UserResourceService<CompletedExercise> {

    public CompletedExerciseService(UserResourceRepository<CompletedExercise> userResourceRepository, UserService userService, UserResourceSpecificationFactory<CompletedExercise> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }
}
