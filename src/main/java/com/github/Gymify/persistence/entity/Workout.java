package com.github.Gymify.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class Workout extends UserResource {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Period period;

    @Builder
    public Workout(Period period, User user) {
        this.period = period;
        this.user = user;
    }
}
