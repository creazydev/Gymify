package com.github.Gymify.core.service;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.Set;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ExerciseService extends UserResourceService<Exercise> implements PrePersistEntityValidator<Exercise> {
    private final UserResourceListParentDelegate<Set> setListParentDelegate;

    public ExerciseService(UserResourceRepository<Exercise> userResourceRepository,
                           UserService userService,
                           UserResourceSpecificationFactory<Exercise> specificationFactory, UserResourceListParentDelegate<Set> setListParentDelegate) {
        super(userResourceRepository, userService, specificationFactory);
        this.setListParentDelegate = setListParentDelegate;
    }

    @Override
    public void validateEntity(Exercise obj) {
        if (Objects.isNull(obj)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Exercise cannot be null!");
        } else if (Objects.isNull(obj.getSets())) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Exercise sets cannot be null!");
        } else if (Strings.isBlank(obj.getName())) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Exercise name cannot be blank!");
        }
    }
}
