package com.github.Gymify.account.model;

import lombok.Data;

@Data
public class LoginRequest {
    private final String email;
    private final String password;
}
