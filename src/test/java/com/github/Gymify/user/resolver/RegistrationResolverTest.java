package com.github.Gymify.user.resolver;

import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.security.service.JwtService;
import com.github.Gymify.security.service.UserService;
import com.github.Gymify.user.RegistrationService;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationResolverTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("email@test.com", "test1234", List.of(UserAuthority.BASIC_USER));
    }

    @Test
    void register() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/register.graphql");
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.register.email")).isEqualTo(user.getEmail());
        assertThat(response.get("$.data.register.authenticationToken")).isNotNull();
    }
}
