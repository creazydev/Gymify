package com.github.Gymify.user.resolver;

import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.security.service.JwtService;
import com.github.Gymify.security.service.UserService;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationResolverTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    private User user;
    private UsernamePasswordAuthenticationToken authentication;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        this.jwtToken = "JWTTOKEN";
        this.user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
        this.authentication = new UsernamePasswordAuthenticationToken(this.user.getUsername(), this.user.getPassword());
    }

    @Test
    void login_validCredentials_returnAuthenticatedUser() throws IOException {
        doReturn(this.authentication).when(this.authenticationProvider).authenticate(this.authentication);
        doReturn(this.user).when(this.userService).getCurrentUser();
        doReturn(this.jwtToken).when(this.jwtService).getToken(this.user);

        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/login.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.login.email")).isNotNull();
        assertThat(response.get("$.data.login.authenticationToken")).isEqualTo(this.jwtToken);
    }

    @Test
    void login_blankPassword_returnErrorMessage() throws IOException {
        doThrow(new BadCredentialsException("")).when(this.authenticationProvider).authenticate(
            new UsernamePasswordAuthenticationToken("email@test.com", ""));

        GraphQLResponse response = this.graphQLTestTemplate.postForResource("graphql/login_blankPassword.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.errors[0].message")).contains("400 BAD_REQUEST");
    }
}
