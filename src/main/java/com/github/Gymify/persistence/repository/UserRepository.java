package com.github.Gymify.persistence.repository;

import com.github.Gymify.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String string);
    boolean existsByEmail(String string);
}
