package com.github.Gymify.user;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    private RegistrationService registrationService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.registrationService = new RegistrationService(userRepository);
        this.user = new User("email@test.com", "test1234", List.of(UserAuthority.BASIC_USER));
    }

    private User user;

    @Test
    void shouldPassWhenMeetConditions() {
        doReturn(false).when(userRepository).existsByEmail(user.getEmail());
        User registeredUser = new User("email@test.com", "test1234", List.of(UserAuthority.BASIC_USER));
        registeredUser.setId(1L);
        doReturn(registeredUser).when(userRepository).save(user);
        assertEquals(registrationService.register(user), registeredUser);
    }

    @Test
    void shouldThrowExceptionWhenInvalidEmail() {
        User user = new User("emailtestxcom", "test1234", List.of(UserAuthority.BASIC_USER));
        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> registrationService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenNullEmail() {
        User user = new User(null, "test1234", List.of(UserAuthority.BASIC_USER));
        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> registrationService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenInvalidPassword() {
        User user = new User("email@test.com", "test123", List.of(UserAuthority.BASIC_USER));
        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> registrationService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsTaken() {
        doReturn(true).when(userRepository).existsByEmail(user.getEmail());
        User user = new User("email@test.com", "test1234", List.of(UserAuthority.BASIC_USER));
        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> registrationService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenUserHasNoRole() {
        doReturn(false).when(userRepository).existsByEmail(user.getEmail());
        User user = new User("email@test.com", "test1234", List.of());
        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> registrationService.register(user));
    }

    @Test
    void shouldThrowExceptionWhenUserHasNoBasicRole() {
        doReturn(false).when(userRepository).existsByEmail(user.getEmail());
        User user = new User("email@test.com", "test1234", List.of(UserAuthority.ADMIN));
        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> registrationService.register(user));
    }
}
