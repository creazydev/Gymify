package com.github.Gymify.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;

    private User user;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository, jwtService);
        this.jwtToken = "JWTTOKEN";
        this.user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
    }

    @Test
    void loadUserByUsername() {
        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());

        UserDetails userDetails = this.userService.loadUserByUsername(user.getEmail());
        assertThat(userDetails).isEqualTo(user);
    }

    @Test
    void loadUserByToken() {
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        doReturn(Optional.of(decodedJWT)).when(jwtService).getDecodedToken(jwtToken);
        doReturn(user.getEmail()).when(decodedJWT).getSubject();
        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());

        UserDetails userDetails = this.userService.loadUserByToken(jwtToken);
        assertThat(userDetails).isEqualTo(user);
    }

    @Test
    void findCurrentUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        doReturn(user.getEmail()).when(authentication).getName();

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());

        Optional<User> currentUser = this.userService.findCurrentUser();
        assertThat(currentUser).isEqualTo(Optional.of(user));
    }
}
