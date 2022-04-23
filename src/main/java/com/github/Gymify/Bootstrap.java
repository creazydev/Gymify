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
import lombok.RequiredArgsConstructor;
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

    @PostConstruct
    void init() {
        this.addSampleUsers();
        this.addSampleWorkoutPlans();
    }

    private void addSampleUsers() {
        Stream.of(
                User.builder()
                    .email("test@mail.ru")
                    .password(passwordEncoder.encode("sample"))
                    .authorities(Set.of(UserAuthority.BASIC_USER))
                    .build()
            )
            .sequential()
            .forEachOrdered(userRepository::save);
    }

    private void addSampleWorkoutPlans() {
        User testUser = this.userRepository.findByEmail("test@mail.ru").orElseThrow(IllegalStateException::new);
        Stream.of(
            WorkoutPlan.builder()
                .name("Test active plan")
                .active(true)
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
