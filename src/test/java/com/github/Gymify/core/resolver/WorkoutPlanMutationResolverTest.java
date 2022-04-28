package com.github.Gymify.core.resolver;

import com.github.Gymify.core.service.WorkoutPlanService;
import com.github.Gymify.persistence.entity.*;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkoutPlanMutationResolverTest {

    @MockBean
    WorkoutPlanService workoutPlanService;

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private WorkoutPlan workoutPlan;
    private WorkoutSession workoutSession;
    private Exercise exercise;
    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User("test@gmail.com", "!password", List.of(UserAuthority.BASIC_USER));

        Equipment equipment = new Equipment();
        equipment.setId(2L);
        equipment.setWeight(20L);
        equipment.setName("Basket");
        equipment.setUser(this.user);

        this.exercise = new Exercise();
        this.exercise.setId(1L);
        this.exercise.setName("Bench press");
        this.exercise.setUser(this.user);
        this.exercise.setEquipment(equipment);
        this.exercise.setPlannedRestDuration(240);

        this.workoutSession = new WorkoutSession();
        this.workoutSession.setId(1L);
        this.workoutSession.setUser(this.user);
        this.workoutSession.setDayOfWeek(DayOfWeek.MONDAY);
        this.workoutSession.setDuration(Duration.ZERO);
        this.workoutSession.setStartTime(LocalTime.MIDNIGHT);
        this.workoutSession.setExercises(List.of(this.exercise));

        this.workoutPlan = new WorkoutPlan();
        this.workoutPlan.setId(1L);
        this.workoutPlan.setUser(this.user);
        this.workoutPlan.setWorkoutSessions(List.of(this.workoutSession));
        this.workoutPlan.setActive(false);
        this.workoutPlan.setName("FBW");
    }

    @Test
    void addWorkoutPlan() throws IOException {
        doReturn(workoutPlan).when(workoutPlanService).add(workoutPlan);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/addWorkoutPlan.graphql");
        assertThat(response.isOk()).isTrue();

        assertThat(response.get("$.addWorkoutPlan")).isEqualTo(this.workoutPlan);
    }
A
    @Test
    void updateWorkoutPlan() {
    }

    @Test
    void activateWorkoutPlan() {
    }

    @Test
    void deleteWorkoutPlan() {
    }
}
