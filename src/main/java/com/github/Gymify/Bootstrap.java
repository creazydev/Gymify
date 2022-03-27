package com.github.Gymify;

import com.github.Gymify.persistence.entity.User;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Stream;

@Component
//@DependsOn({"securityConfiguration"})
@RequiredArgsConstructor
public class Bootstrap {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    void addSampleUsers() {
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
}
