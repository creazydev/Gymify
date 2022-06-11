package com.github.Gymify.user;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        this.registrationService = new RegistrationService(this.userRepository);
        this.user = new User("email@test.com", "test1234", List.of(UserAuthority.BASIC_USER));
    }

    @Test
    void register_valid_returnRegisteredUser() {
        User registeredUser = new User("email@test.com", "test1234", List.of(UserAuthority.BASIC_USER));
        registeredUser.setId(1L);

        doReturn(false).when(this.userRepository).existsByEmail(this.user.getEmail());
        doReturn(registeredUser).when(this.userRepository).save(this.user);

        assertEquals(this.registrationService.register(this.user), registeredUser);
    }

    @Test
    void register_invalidEmail_throwRuntimeExceptionWhileDataFetching() {
        User user = new User("emailtestxcom", "test1234", List.of(UserAuthority.BASIC_USER));

        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> this.registrationService.register(user));
    }

    @Test
    void register_emailNull_throwRuntimeExceptionWhileDataFetching() {
        User user = new User(null, "test1234", List.of(UserAuthority.BASIC_USER));

        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> this.registrationService.register(user));
    }

    @Test
    void register_invalidPassword_throwRuntimeExceptionWhileDataFetching() {
        User user = new User("email@test.com", "test123", List.of(UserAuthority.BASIC_USER));

        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> this.registrationService.register(user));
    }

    @Test
    void register_emailTaken_throwRuntimeExceptionWhileDataFetching() {
        User user = new User("email@test.com", "test1234", List.of(UserAuthority.BASIC_USER));

        doReturn(true).when(this.userRepository).existsByEmail(user.getEmail());

        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> this.registrationService.register(user));
    }

    @Test
    void register_noUserAuthority_throwRuntimeExceptionWhileDataFetching() {
        doReturn(false).when(userRepository).existsByEmail(user.getEmail());
        User user = new User("email@test.com", "test1234", List.of());
        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> registrationService.register(user));
    }

    @Test
    void register_noBasicUserAuthority_throwRuntimeExceptionWhileDataFetching() {
        doReturn(false).when(userRepository).existsByEmail(user.getEmail());
        User user = new User("email@test.com", "test1234", List.of(UserAuthority.ADMIN));
        assertThrows(RuntimeExceptionWhileDataFetching.class, () -> registrationService.register(user));
    }
}
