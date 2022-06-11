package com.github.Gymify.core.resolver;

import com.github.Gymify.configuration.GLogger;
import com.github.Gymify.core.service.WorkoutPlanService;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.security.service.UserService;
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
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QueryResolverTest implements GLogger {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private UserService userService;

    @MockBean
    private WorkoutPlanService workoutPlanService;

    private User user;
    private WorkoutPlan workoutPlan;

    @BeforeEach
    void setUp() {
        this.user = new User("test@mail.ru", "!password", List.of(UserAuthority.BASIC_USER));

        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setId(2L);

        this.workoutPlan = new WorkoutPlan();
        this.workoutPlan.setWorkoutSessions(List.of(workoutSession));
        this.workoutPlan.setActive(false);
        this.workoutPlan.setName("FBW");
        this.workoutPlan.setId(1L);
    }

    @Test
    void getWorkoutPlanById_exists_returnValidJson() throws IOException {
        doReturn(this.workoutPlan).when(this.workoutPlanService).getOrThrowNotFound(this.workoutPlan.getId());

        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/getWorkoutPlanById.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.getWorkoutPlanById.id")).isEqualTo(String.valueOf(this.workoutPlan.getId().intValue()));
        assertThat(response.get("$.data.getWorkoutPlanById.name")).isEqualTo(this.workoutPlan.getName());
        assertThat(response.get("$.data.getWorkoutPlanById.workoutSessions[0].id")).isNotNull();
        assertThat(response.get("$.data.getWorkoutPlanById.active")).isEqualTo(String.valueOf(this.workoutPlan.getActive()));
    }
}
