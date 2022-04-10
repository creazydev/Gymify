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
public class CompletedSet extends Set {

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "actual_rest_duration", nullable = false)
    private Integer actualRestDuration;
}
