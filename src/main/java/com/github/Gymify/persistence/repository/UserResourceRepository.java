package com.github.Gymify.persistence.repository;

import com.github.Gymify.persistence.entity.UserResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserResourceRepository<T extends UserResource> extends JpaRepository<T, Long>,
    JpaSpecificationExecutor<T> {

    List<T> findAllByUserId(Long userId);
}
