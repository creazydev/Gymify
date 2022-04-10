package com.github.Gymify.user;

import com.github.Gymify.exception.RuntimeExceptionWhileDataFetching;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
import com.github.Gymify.user.util.PasswordValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Predicate;

@Service

@Transactional
public class RegistrationService {
    private final Predicate<User> uniqueEmailPredicate;
    private final Predicate<User> validEmailPredicate;
    private final Predicate<User> minimalRolePredicate;
    private final Predicate<User> validPasswordPredicate;

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;

    public RegistrationService(UserRepository userRepository) {
        this.emailValidator = EmailValidator.getInstance(true);
        this.userRepository = userRepository;
        this.uniqueEmailPredicate = (User) -> !this.userRepository.existsByEmail(User.getEmail());
        this.validEmailPredicate = (User) -> this.emailValidator.isValid(User.getEmail());
        this.minimalRolePredicate = (User) -> User.getAuthorities().contains(UserAuthority.BASIC_USER);
        this.validPasswordPredicate = (User) -> new PasswordValidator().test(User.getPassword());
    }

    public User register(User user) {
        user.setId(null);
        if (!this.validEmailPredicate.test(user)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Invalid email!");
        } else if (!this.validPasswordPredicate.test(user)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Invalid password!");
        } else if (!this.uniqueEmailPredicate.test(user)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "Email in use!");
        } else if (!this.minimalRolePredicate.test(user)) {
            throw new RuntimeExceptionWhileDataFetching(HttpStatus.BAD_REQUEST, "User must have at least basic user role!");
        }
        return this.userRepository.save(user);
    }
}
