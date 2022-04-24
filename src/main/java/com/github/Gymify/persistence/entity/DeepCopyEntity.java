package com.github.Gymify.persistence.entity;

@FunctionalInterface
public interface DeepCopyEntity {
    Object deepCopy();
}
