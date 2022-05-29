package com.github.Gymify.persistence.entity;

import com.github.Gymify.persistence.enums.BodyPart;
import com.github.Gymify.persistence.type.EnumSetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@TypeDef(
    name = "enum-set",
    typeClass = EnumSetType.class
)

@NoArgsConstructor
@Getter
@Setter
public class Exercise extends UserResource implements DeepCopyEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Type(
        type = "enum-set",
        parameters = {@Parameter(name = "enumClass", value = "com.github.Gymify.persistence.enums.BodyPart")}
    )
    private EnumSet<BodyPart> bodyParts;

    @OneToMany(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private List<Set> sets;

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @Column(name = "planned_rest_duration")
    private Integer plannedRestDuration;

    @Override
    public Exercise deepCopy() {
        Exercise exercise = new Exercise();
        exercise.name = this.name;
        exercise.bodyParts = this.bodyParts;
        exercise.equipment = this.equipment;
        exercise.plannedRestDuration = this.plannedRestDuration;
        exercise.user = this.user;
        exercise.sets = this.sets.stream()
                .map(Set::deepCopy)
                .collect(Collectors.toList());

        return exercise;
    }
}
