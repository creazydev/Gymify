package com.github.Gymify.account.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class AuthenticatedUser {
    private final String email;

    public AuthenticatedUser(UserDetails userDetails) {
        this.email = userDetails.getUsername();
    }
}
