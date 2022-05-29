package com.github.Gymify.core.service;

@FunctionalInterface
public interface PrePersistEntityValidator<T> {
    void validateEntity(T obj);
}
