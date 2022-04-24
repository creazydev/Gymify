package com.github.Gymify;

import com.github.Gymify.core.service.WorkoutPlanService;
import com.github.Gymify.persistence.entity.Period;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.entity.WorkoutPlan;
import com.github.Gymify.persistence.entity.WorkoutSession;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
import com.github.Gymify.persistence.repository.WorkoutPlanRepository;
import com.github.Gymify.persistence.repository.WorkoutSessionRepository;
import com.github.Gymify.user.resolver.AuthenticationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
@Transactional

@RequiredArgsConstructor
public class Bootstrap {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkoutSessionRepository workoutRepository;
    private final WorkoutPlanService workoutPlanService;
    private final AuthenticationResolver authenticationResolver;

    private final static String TEST_USER_EMAIL = "test@mail.ru";
    private final static String TEST_USER_PASSWORD = "sample";

    @PostConstruct
    void init() {
        this.addSampleUsers();
        this.addSampleWorkoutPlans();
    }

    private void addSampleUsers() {
        Stream.of(
                User.builder()
                    .email(TEST_USER_EMAIL)
                    .password(passwordEncoder.encode(TEST_USER_PASSWORD))
                    .authorities(Set.of(UserAuthority.BASIC_USER))
                    .build()
            )
            .sequential()
            .forEachOrdered(userRepository::save);
    }

    private void addSampleWorkoutPlans() {
        User testUser = this.userRepository.findByEmail(TEST_USER_EMAIL).orElseThrow(IllegalStateException::new);

        Stream.of(
            WorkoutPlan.builder()
                .name("Test active plan")
                .active(false)
                .workoutSessions(List.of())
                .user(testUser)
                .build(),
            WorkoutPlan.builder()
                .name("Test nonactive plan")
                .active(false)
                .workoutSessions(List.of())
                .user(testUser)
                .build()
        )
            .sequential()
            .forEachOrdered(workoutPlanService::add);
    }
}
