package com.github.Gymify;

import com.github.Gymify.persistence.entity.Period;
import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.entity.Workout;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
import com.github.Gymify.persistence.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

@Component
@Transactional

@RequiredArgsConstructor
public class Bootstrap {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkoutRepository workoutRepository;

    @PostConstruct
    void init() {
        this.addSampleUsers();
        this.addSampleWorkouts();
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

    private void addSampleWorkouts() {
        User testUser = this.userRepository.findByEmail("test@mail.ru").orElseThrow(IllegalStateException::new);
        this.workoutRepository.findAll();
        Stream.of(
                Workout.builder()
                    .user(testUser)
                    .period(Period.of(LocalDateTime.now(), LocalDateTime.now().plusHours(2)))
                    .build()
            )
            .sequential()
            .forEachOrdered(workoutRepository::save);
    }
}
