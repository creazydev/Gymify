package com.github.Gymify.account.service;

import com.github.Gymify.account.model.AuthenticatedUser;
import com.github.Gymify.account.model.LoginRequest;
import com.github.Gymify.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticatedUser login(@RequestBody @Valid LoginRequest loginRequest) {
        UserDetails userDetails = this.userService.loadUserByUsername(loginRequest.getEmail());

        if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            return new AuthenticatedUser(userDetails);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }
}
