package com.github.Gymify.security.filter;

import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.security.service.JwtService;
import com.github.Gymify.security.service.UserService;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtFilterTest {

    private JwtFilter jwtFilter;
    private HttpServletRequest httpServletRequest;

    @MockBean
    private UserService userService;

    private User user;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        this.jwtFilter = new JwtFilter(userService);
        this.jwtToken = "JWTTOKEN";
        this.user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
        this.httpServletRequest = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    void doFilterInternal_validJwt_contextAuthenticationNotNull() throws ServletException, IOException {
        doReturn(Optional.of(this.user)).when(this.userService).findUserByToken(this.jwtToken);
        doReturn("Bearer " + this.jwtToken).when(this.httpServletRequest).getHeader("Authorization");

        this.jwtFilter.doFilterInternal(this.httpServletRequest, Mockito.mock(HttpServletResponse.class), new MockFilterChain());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
    }

    @Test
    void doFilterInternal_authenticationHeaderNull_contextAuthenticationNull() throws ServletException, IOException {
        doReturn(null).when(this.httpServletRequest).getHeader("Authorization");

        this.jwtFilter.doFilterInternal(this.httpServletRequest, Mockito.mock(HttpServletResponse.class), new MockFilterChain());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternal_invalidJwt_contextAuthenticationNull() throws ServletException, IOException {
        doReturn("Bearer " + "INVALID").when(this.httpServletRequest).getHeader("Authorization");

        this.jwtFilter.doFilterInternal(this.httpServletRequest, Mockito.mock(HttpServletResponse.class), new MockFilterChain());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternal_notFoundUser_contextAuthenticationNull() throws ServletException, IOException {
        doReturn(Optional.empty()).when(this.userService).findUserByToken(this.jwtToken);
        doReturn("Bearer " + this.jwtToken).when(this.httpServletRequest).getHeader("Authorization");

        this.jwtFilter.doFilterInternal(this.httpServletRequest, Mockito.mock(HttpServletResponse.class), new MockFilterChain());
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}
