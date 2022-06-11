package com.github.Gymify.core.service;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.Set;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SetService extends UserResourceService<Set> implements PrePersistEntityValidator<Set> {

    public SetService(UserResourceRepository<Set> userResourceRepository, UserService userService, UserResourceSpecificationFactory<Set> specificationFactory) {
        super(userResourceRepository, userService, specificationFactory);
    }

    @Override
    public Set update(Set set) {
        return this.find(set.getId())
            .stream()
            .peek(savedSet -> {
                savedSet.setRepsQuantity(set.getRepsQuantity());
                savedSet.setPlannedRestDuration(set.getPlannedRestDuration());
            })
            .peek(this::validateEntity)
            .map(super::update)
            .findFirst()
            .orElseGet(() -> this.add(set));
    }

    @Override
    public void validateEntity(Set obj) {
        if (Objects.isNull(obj)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Set cannot be null!");
        } else if (Objects.isNull(obj.getRepsQuantity())) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Reps quantity cannot be null!");
        } else if (obj.getRepsQuantity() < 0) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Reps quantity must be positive!");
        }
    }
}
