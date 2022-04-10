package com.github.Gymify.persistence.entity;

import com.github.Gymify.persistence.type.EnumSetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@TypeDef(
    name = "enum-set",
    typeClass = EnumSetType.class
)

@NoArgsConstructor
@Getter
@Setter
public class CompletedExercise extends Exercise {

    @OneToMany(
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "completed_exercise_id", referencedColumnName = "id")
    private List<CompletedSet> completedSets;

    @Column(name = "actual_rest_duration")
    private Integer actualRestDuration;
}
