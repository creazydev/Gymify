package com.github.Gymify.persistence.auditing;

import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@RequiredArgsConstructor
public class ApplicationAuditorAware implements AuditorAware<Long> {
    private final UserService userService;

    @Override
    public @NotNull Optional<Long> getCurrentAuditor() {
        try {
            return this.userService
                .findCurrentUser()
                .map(User::getId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
