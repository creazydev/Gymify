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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    private UserService userService;
    private User user;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(this.userRepository, this.jwtService);
        this.jwtToken = "JWTTOKEN";
        this.user = new User("email@test.com", "XXX", List.of(UserAuthority.BASIC_USER));
    }

    @Test
    void loadUserByUsername_usernameNotExists_throwUsernameNotFoundException() {
        doReturn(Optional.empty()).when(this.userRepository).findByEmail(this.user.getEmail());
        assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername(this.user.getEmail()));
    }

    @Test
    void loadUserByUsername_usernameNull_throwUsernameNotFoundException() {
        doReturn(Optional.empty()).when(this.userRepository).findByEmail(null);
        assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername(null));
    }

    @Test
    void loadUserByUsername_usernameExists_returnUser() {
        doReturn(Optional.of(this.user)).when(this.userRepository).findByEmail(this.user.getEmail());
        UserDetails userDetails = this.userService.loadUserByUsername(this.user.getEmail());
        assertThat(userDetails).isEqualTo(this.user);
    }

    @Test
    void findUserByToken_decodingFailed_returnEmpty() {
        doReturn(Optional.empty()).when(this.jwtService).getDecodedToken(null);
        Optional<UserDetails> userByToken = this.userService.findUserByToken(null);
        assertThat(userByToken).isEmpty();
    }

    @Test
    void findUserByToken_tokenValidUsernameExists_returnUserDetails() {
        final DecodedJWT decodedJWTMock = Mockito.mock(DecodedJWT.class);
        final String subject = "email";

        doReturn(Optional.of(decodedJWTMock)).when(this.jwtService).getDecodedToken(this.jwtToken);
        doReturn(subject).when(decodedJWTMock).getSubject();
        doReturn(Optional.of(this.user)).when(this.userRepository).findByEmail(subject);

        assertThat(this.userService.findUserByToken(this.jwtToken)).contains(this.user);
    }

    @Test
    void findCurrentUser_noAuthentication_returnEmpty() {
        assertThat(this.userService.findCurrentUser()).isEmpty();
    }

    @Test
    void findCurrentUser_authenticationSetEmailNotExists_returnEmpty() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        doReturn(this.user.getEmail()).when(authentication).getName();
        doReturn(authentication).when(securityContext).getAuthentication();
        doReturn(Optional.empty()).when(this.userRepository).findByEmail(this.user.getEmail());

        assertThat(this.userService.findCurrentUser()).isEmpty();
    }

    @Test
    void findCurrentUser_authenticationSetEmailExists_returnUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        doReturn(this.user.getEmail()).when(authentication).getName();
        doReturn(authentication).when(securityContext).getAuthentication();
        doReturn(Optional.of(this.user)).when(this.userRepository).findByEmail(this.user.getEmail());

        assertThat(this.userService.findCurrentUser()).contains(this.user);
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
}
