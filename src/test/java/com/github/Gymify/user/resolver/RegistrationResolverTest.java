package com.github.Gymify.user.resolver;

import com.github.Gymify.configuration.GLogger;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
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
class RegistrationResolverTest implements GLogger {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User("email@test.com", "test1234", List.of(UserAuthority.BASIC_USER));
    }

    @Test
    void register_validCredentials_validResponse() throws IOException {
        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/register.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.register.email")).isEqualTo(this.user.getEmail());
        assertThat(response.get("$.data.register.authenticationToken")).isNotNull();
    }

    @Test
    void register_invalidPassword_hasErrorMessage() throws IOException {
        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/register_invalidPassword.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("400 BAD_REQUEST");
        assertThat(response.get("$.errors[0].message")).contains("Invalid password!");
    }
}
