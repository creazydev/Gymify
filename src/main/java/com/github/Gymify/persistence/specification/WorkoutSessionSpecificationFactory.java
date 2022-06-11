package com.github.Gymify.persistence.specification;

import com.github.Gymify.persistence.entity.WorkoutSession;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Component
public class WorkoutSessionSpecificationFactory extends UserResourceSpecificationFactory<WorkoutSession> {

    public Specification<WorkoutSession> durationLongerThan(Duration duration) {
        return (root, cq, cb) -> cb.greaterThan(root.get("creationTimestamp"), duration);
    }

    public Specification<WorkoutSession> startTimeAfter(LocalTime startTime) {
        return (root, cq, cb) -> cb.greaterThan(root.get("startTime"), startTime);
    }

    public Specification<WorkoutSession> dayOfWeekEquals(DayOfWeek dayOfWeek) {
        return (root, cq, cb) -> cb.equal(root.get("dayOfWeek"), dayOfWeek);
    }
}
