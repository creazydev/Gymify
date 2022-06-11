package com.github.Gymify.core.service;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.Set;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    public Exercise add(Exercise obj) {
        this.validateEntity(obj);
        obj.setSets(this.setListParentDelegate.updateCollection(Collections.emptyList(), obj.getSets()));
        return super.add(obj);
    }

    @Override
    public Exercise update(Exercise exercise) {
        return this.find(exercise.getId())
            .stream()
            .peek(savedExercise -> {
                savedExercise.setName(exercise.getName());
                savedExercise.setPlannedRestDuration(exercise.getPlannedRestDuration());
                savedExercise.setSets(
                    this.setListParentDelegate.updateCollection(
                        savedExercise.getSets(), exercise.getSets()
                    )
                );
            })
            .peek(this::validateEntity)
            .map(super::update)
            .findFirst()
            .orElseGet(() -> this.add(exercise));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.find(id)
            .ifPresent(exercise -> {
                this.setListParentDelegate.updateCollection(exercise.getSets(), Collections.emptyList());
                super.delete(id);
            });
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
