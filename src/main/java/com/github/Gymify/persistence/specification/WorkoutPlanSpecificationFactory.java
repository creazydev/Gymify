package com.github.Gymify.persistence.specification;

import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class WorkoutPlanSpecificationFactory extends UserResourceSpecificationFactory<WorkoutPlan> {

    public Specification<WorkoutPlan> active() {
        return (root, cq, cb) -> cb.isTrue(root.get("active"));
    }
}
