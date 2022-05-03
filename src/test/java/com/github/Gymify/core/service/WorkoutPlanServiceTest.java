package com.github.Gymify.core.service;

import com.github.Gymify.core.workout.ScheduledWorkoutSessionAggregator;
import com.github.Gymify.persistence.entity.*;
import com.github.Gymify.persistence.repository.WorkoutPlanRepository;
import com.github.Gymify.persistence.specification.WorkoutPlanSpecificationFactory;
import com.github.Gymify.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class WorkoutPlanServiceTest {

    @Mock
    UserService userService;

    @Mock
    WorkoutPlanRepository workoutPlanRepository;

    @Mock
    ScheduledWorkoutSessionService scheduledWorkoutSessionService;

    @Mock
    ScheduledWorkoutSessionAggregator scheduledWorkoutSessionAggregator;

    private WorkoutPlanService workoutPlanService;

    private WorkoutPlan workoutPlan;
    private User user;

    @BeforeEach
    void setUp() {
        this.workoutPlanService = new WorkoutPlanService(
                this.workoutPlanRepository,
                this.userService,
                new WorkoutPlanSpecificationFactory(),
                this.scheduledWorkoutSessionService,
                this.scheduledWorkoutSessionAggregator
        );

        Equipment equipment = new Equipment();
        equipment.setId(2L);
        equipment.setWeight(20L);
        equipment.setName("Basket");
        equipment.setUser(user);

        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Bench press");
        exercise.setUser(user);
        exercise.setEquipment(equipment);
        exercise.setPlannedRestDuration(240);

        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setId(1L);
        workoutSession.setUser(user);
        workoutSession.setDayOfWeek(DayOfWeek.MONDAY);
        workoutSession.setDuration(Duration.ZERO);
        workoutSession.setStartTime(LocalTime.MIDNIGHT);
        workoutSession.setExercises(List.of(exercise));

        this.workoutPlan = new WorkoutPlan();
        this.workoutPlan.setId(1L);
        this.workoutPlan.setUser(this.user);
        this.workoutPlan.setWorkoutSessions(
                List.of(workoutSession)
        );
        this.workoutPlan.setActive(false);
        this.workoutPlan.setName("FBW");
    }

    @Test
    void add() {
        doReturn(this.workoutPlan).when(this.workoutPlanRepository).save(this.workoutPlan);
//        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());

        WorkoutPlan workoutPlan = this.workoutPlanService.add(this.workoutPlan);
        assertThat(workoutPlan).isEqualTo(user);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void validateEntity() {
    }

    @Test
    void getSpecificationFactory() {
    }

    @Test
    void activate() {
    }
}