package com.github.Gymify.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class Equipment extends UserResource {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight")
    private Long weight;
}
