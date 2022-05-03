package com.github.Gymify.core.resolver;

import com.github.Gymify.configuration.GLogger;
import com.github.Gymify.core.service.WorkoutPlanService;
import com.github.Gymify.persistence.entity.*;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
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

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkoutPlanMutationResolverTest implements GLogger {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private static WorkoutPlan workoutPlan;
    private static WorkoutSession workoutSession;
    private static Exercise exercise;
    private static User user;

    @BeforeEach
    void setUp() {
        user = new User("test@gmail.com", "!password", List.of(UserAuthority.BASIC_USER));

        Equipment equipment = new Equipment();
        equipment.setId(2L);
        equipment.setWeight(20L);
        equipment.setName("Basket");
        equipment.setUser(user);

        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Bench press");
        exercise.setUser(user);
        exercise.setEquipment(equipment);
        exercise.setPlannedRestDuration(240);

        workoutSession = new WorkoutSession();
        workoutSession.setId(1L);
        workoutSession.setUser(user);
        workoutSession.setDayOfWeek(DayOfWeek.MONDAY);
        workoutSession.setDuration(Duration.ZERO);
        workoutSession.setStartTime(LocalTime.MIDNIGHT);
        workoutSession.setExercises(List.of(exercise));

        workoutPlan = new WorkoutPlan();
//        workoutPlan.setId(1L);
        workoutPlan.setUser(user);
//        workoutPlan.setWorkoutSessions(List.of(workoutSession));
        workoutPlan.setActive(false);
        workoutPlan.setName("FBW");
    }

    @Test
    void addWorkoutPlan() throws IOException {
        GraphQLResponse response = this.graphQLTestTemplate
                .postForResource("graphql/addWorkoutPlan.graphql");

        assertThat(response.isOk()).isTrue();


        debug(response.getRawResponse().getBody());
        assertThat(response.get("$.data.addWorkoutPlan.id")).isEqualTo(workoutPlan.getId());
        assertThat(response.get("$.data.addWorkoutPlan.creationTimestamp")).isEqualTo(workoutPlan.getCreationTimestamp());
        assertThat(response.get("$.data.addWorkoutPlan.updateTimestamp")).isEqualTo(workoutPlan.getUpdateTimestamp());
        assertThat(response.get("$.data.addWorkoutPlan.name")).isEqualTo(workoutPlan.getName());
        assertThat(response.get("$.data.addWorkoutPlan.active")).isEqualTo(String.valueOf(workoutPlan.getActive()));
    }

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
