package com.github.Gymify.user.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class AuthenticatedUser  {
    private final String email;
    private final String authenticationToken;

    public AuthenticatedUser(UserDetails userDetails, String authenticationToken) {
        this.email = userDetails.getUsername();
        this.authenticationToken = authenticationToken;
    }
}
