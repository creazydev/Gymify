package com.github.Gymify.core.resolver;

import com.github.Gymify.configuration.GLogger;
import com.github.Gymify.persistence.entity.Exercise;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkoutPlanMutationResolverTest implements GLogger {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private WorkoutPlan workoutPlan;
    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User("test@mail.ru", "!password", List.of(UserAuthority.BASIC_USER));

        this.workoutPlan = new WorkoutPlan();
        this.workoutPlan.setWorkoutSessions(List.of());
        this.workoutPlan.setActive(false);
        this.workoutPlan.setName("FBW");
    }

    @Test
    @WithUserDetails(value = "test@mail.ru")
    void addWorkoutPlan_createNew_returnValidJson() throws IOException {
        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/addWorkoutPlan.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.addWorkoutPlan.id")).isEqualTo(this.workoutPlan.getId());
        assertThat(response.get("$.data.addWorkoutPlan.creationTimestamp")).isEqualTo(this.workoutPlan.getCreationTimestamp());
        assertThat(response.get("$.data.addWorkoutPlan.updateTimestamp")).isEqualTo(this.workoutPlan.getUpdateTimestamp());
        assertThat(response.get("$.data.addWorkoutPlan.name")).isEqualTo(this.workoutPlan.getName());
        assertThat(response.get("$.data.addWorkoutPlan.active")).isEqualTo(String.valueOf(this.workoutPlan.getActive()));
    }
}
