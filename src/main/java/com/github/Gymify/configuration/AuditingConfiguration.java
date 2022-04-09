package com.github.Gymify.configuration;

import com.github.Gymify.persistence.auditing.ApplicationAuditorAware;
import com.github.Gymify.persistence.repository.UserRepository;
import com.github.Gymify.security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(
    auditorAwareRef = "auditorProvider",
    dateTimeProviderRef = "dateTimeProvider"
)
public class AuditingConfiguration {

    @Bean
    AuditorAware<Long> auditorProvider(UserService userService, UserRepository userRepository) {
        return new ApplicationAuditorAware(userService, userRepository);
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }
}
