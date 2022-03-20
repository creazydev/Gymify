package com.github.Gymify.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.type.EnumSetType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.EnumSet;

@Entity
@TypeDef(
    name = "enum-set",
    typeClass = EnumSetType.class
)

@Setter
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private String id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Type(type = "enum-set", parameters = {
        @Parameter(name = "enumClass", value = "com.github.Gymify.persistence.enums.UserAuthority")
    })
    private EnumSet<UserAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
