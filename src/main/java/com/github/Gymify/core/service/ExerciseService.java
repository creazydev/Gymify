package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService extends UserResourceService<Exercise> {

    public ExerciseService(UserResourceRepository<Exercise> userResourceRepository,
                           UserService userService,
                           UserResourceSpecificationFactory<Exercise> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }
}
