package com.github.Gymify.core.service;

import java.util.Optional;

public interface CrudService<T, K> {
    Optional<T> find(K id);
    T add(T obj);
    T update(T obj);
    void delete(K id);
}
