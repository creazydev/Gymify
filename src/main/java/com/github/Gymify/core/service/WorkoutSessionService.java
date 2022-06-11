package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class WorkoutSessionService extends UserResourceService<WorkoutSession> implements PrePersistEntityValidator<WorkoutSession> {

    public WorkoutSessionService(UserResourceRepository<WorkoutSession> userResourceRepository, UserService userService, UserResourceSpecificationFactory<WorkoutSession> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }

    @Override
    public void validateEntity(WorkoutSession obj) {

    }
}
