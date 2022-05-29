package com.github.Gymify.configuration;

import com.github.Gymify.persistence.auditing.ApplicationAuditorAware;
import com.github.Gymify.security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(
    auditorAwareRef = "auditorProvider"
)
public class AuditingConfiguration {

    @Bean
    AuditorAware<Long> auditorProvider(UserService userService) {
        return new ApplicationAuditorAware(userService);
    }
}
