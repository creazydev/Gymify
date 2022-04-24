package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.OnGoingExercise;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class OnGoingExerciseService extends UserResourceService<OnGoingExercise> {

    public OnGoingExerciseService(UserResourceRepository<OnGoingExercise> userResourceRepository,
                                    UserService userService,
                                    UserResourceSpecificationFactory<OnGoingExercise> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }
}
