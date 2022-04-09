package com.github.Gymify.core.service;

import com.github.Gymify.persistence.entity.UserResource;
import com.github.Gymify.persistence.repository.UserResourceRepository;
import com.github.Gymify.persistence.specification.UserResourceSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@RequiredArgsConstructor
public abstract class UserResourceService<T extends UserResource> {
    protected final UserResourceRepository<T> userResourceRepository;
    protected final UserService userService;
    protected final UserResourceSpecificationFactory<T> specificationFactory;

    public List<T> getAllByCurrentUserId() {
        return this.userResourceRepository.findAllByUserId(userService.getCurrentUser().getId());
    }

    public Page<T> getAllByCurrentUserId(Pageable pageable) {
        return this.userResourceRepository.findAll(
            this.specificationFactory.userIdEquals(userService.getCurrentUser().getId()),
            pageable
        );
    }

    public Page<T> getAllByCurrentUserId(Pageable pageable, Specification<T> specification) {
        return this.userResourceRepository.findAll(
            specification.and(this.specificationFactory.userIdEquals(userService.getCurrentUser().getId())),
            pageable
        );
    }
}
