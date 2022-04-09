package com.github.Gymify.persistence.auditing;

import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.repository.UserRepository;
import com.github.Gymify.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@RequiredArgsConstructor
public class ApplicationAuditorAware implements AuditorAware<Long> {
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public @NotNull Optional<Long> getCurrentAuditor() {
        try {
            UserDetails currentUser = this.userService.getCurrentUser();
            return userRepository
                .findByEmail(currentUser.getUsername())
                .map(User::getId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
