package com.github.Gymify.persistence.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    BASIC_USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
