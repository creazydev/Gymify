package com.github.Gymify.user;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
import com.github.Gymify.user.util.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor

@Transactional
public class RegistrationService {
    private final Predicate<User> UNIQUE_EMAIL_PREDICATE = (User) -> !this.userRepository.existsByEmail(User.getEmail());
    private final Predicate<User> VALID_EMAIL_PREDICATE = (User) -> this.emailValidator.isValid(User.getEmail());
    private final Predicate<User> MINIMAL_ROLE_PREDICATE = (User) -> User.getAuthorities().contains(UserAuthority.BASIC_USER);
    private final Predicate<User> VALID_PASSWORD_PREDICATE = (User) -> new PasswordValidator().test(User.getPassword());

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;

    public User register(User user) {
        user.setId(null);
        if (!VALID_EMAIL_PREDICATE.test(user)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Invalid email!");
        } else if (!VALID_PASSWORD_PREDICATE.test(user)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Invalid password!");
        } else if (!UNIQUE_EMAIL_PREDICATE.test(user)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Email in use!");
        } else if (!MINIMAL_ROLE_PREDICATE.test(user)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "User must have at least basic user role!");
        }
        return this.userRepository.save(user);
    }



}
