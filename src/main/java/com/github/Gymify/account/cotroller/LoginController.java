package com.github.Gymify.account.cotroller;

import com.github.Gymify.account.model.AuthenticatedUser;
import com.github.Gymify.account.model.LoginRequest;
import com.github.Gymify.account.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")

@Transactional
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public AuthenticatedUser login(@RequestBody @Valid LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
