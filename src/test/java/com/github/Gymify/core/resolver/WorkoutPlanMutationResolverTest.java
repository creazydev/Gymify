package com.github.Gymify.core.resolver;

import com.github.Gymify.configuration.GLogger;
import com.github.Gymify.core.service.WorkoutPlanService;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkoutPlanMutationResolverTest implements GLogger {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private WorkoutPlanService workoutPlanService;

    private WorkoutPlan workoutPlan;
    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User("test@mail.ru", "!password", List.of(UserAuthority.BASIC_USER));

        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setId(2L);

        this.workoutPlan = new WorkoutPlan();
        this.workoutPlan.setWorkoutSessions(List.of(workoutSession));
        this.workoutPlan.setActive(false);
        this.workoutPlan.setName("FBW");
    }

    @Test
    void addWorkoutPlan_createNew_returnValidJson() throws IOException {
        doReturn(this.workoutPlan).when(this.workoutPlanService).add(any());

        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/addWorkoutPlan.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.addWorkoutPlan.id")).isEqualTo(this.workoutPlan.getId());
        assertThat(response.get("$.data.addWorkoutPlan.creationTimestamp")).isEqualTo(this.workoutPlan.getCreationTimestamp());
        assertThat(response.get("$.data.addWorkoutPlan.updateTimestamp")).isEqualTo(this.workoutPlan.getUpdateTimestamp());
        assertThat(response.get("$.data.addWorkoutPlan.name")).isEqualTo(this.workoutPlan.getName());
        assertThat(response.get("$.data.getWorkoutPlanById.workoutSessions[0].id")).isNotNull();
        assertThat(response.get("$.data.addWorkoutPlan.active")).isEqualTo(String.valueOf(this.workoutPlan.getActive()));
    }

    @Test
    void updateWorkoutPlan_postObject_returnValidJson() throws IOException {
        doReturn(this.workoutPlan).when(this.workoutPlanService).update(any());

        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/updateWorkoutPlan.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.updateWorkoutPlan.id")).isEqualTo(this.workoutPlan.getId());
        assertThat(response.get("$.data.updateWorkoutPlan.creationTimestamp")).isEqualTo(this.workoutPlan.getCreationTimestamp());
        assertThat(response.get("$.data.updateWorkoutPlan.updateTimestamp")).isEqualTo(this.workoutPlan.getUpdateTimestamp());
        assertThat(response.get("$.data.updateWorkoutPlan.name")).isEqualTo(this.workoutPlan.getName());
        assertThat(response.get("$.data.updateWorkoutPlan.workoutSessions[0].id")).isNotNull();
        assertThat(response.get("$.data.updateWorkoutPlan.active")).isEqualTo(String.valueOf(this.workoutPlan.getActive()));
    }

    @Test
    void deleteWorkoutPlan_deleteById_responseOk() throws IOException {
        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/deleteWorkoutPlan.graphql");

        debug(response.getRawResponse().getBody());

        assertThat(response.isOk()).isTrue();
    }
}
