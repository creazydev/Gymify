package com.github.Gymify.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@NoArgsConstructor
@Getter
@Setter
public class Set extends UserResource implements DeepCopyEntity {

    @Column(name = "reps_quantity", nullable = false)
    private Integer repsQuantity;

    @Column(name = "planned_rest_duration", nullable = false)
    private Integer plannedRestDuration;

    @Override
    public Set deepCopy() {
        Set set = new Set();
        set.repsQuantity = this.repsQuantity;
        set.plannedRestDuration = this.plannedRestDuration;
        set.user = this.user;
        return set;
    }
}
