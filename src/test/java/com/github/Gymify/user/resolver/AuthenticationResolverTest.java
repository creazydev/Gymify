package com.github.Gymify.user.resolver;

import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.security.service.JwtService;
import com.github.Gymify.security.service.UserService;
import com.github.Gymify.user.model.AuthenticatedUser;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationResolverTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    AuthenticationProvider authenticationProvider;

    @MockBean
    UserService userService;

    @MockBean
    JwtService jwtService;

    private static User user;
    private static UsernamePasswordAuthenticationToken authentication;
    private static String jwtToken;

    @BeforeEach
    void setUp() {
        jwtToken = "JWTTOKEN";
        user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
        authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
    }

    @Test
    void login() throws IOException {
        doReturn(authentication).when(authenticationProvider).authenticate(authentication);
        doReturn(user).when(userService).getCurrentUser();
        doReturn(jwtToken).when(jwtService).getToken(user);
        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/login.graphql");
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.login.email")).isNotNull();
        assertThat(response.get("$.data.login.authenticationToken")).isEqualTo(jwtToken);
    }
}
