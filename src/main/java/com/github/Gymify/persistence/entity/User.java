package com.github.Gymify.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.Gymify.persistence.enums.UserAuthority;
import com.github.Gymify.persistence.type.EnumSetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "application_user")

@TypeDef(
    name = "enum-set",
    typeClass = EnumSetType.class
)

@NoArgsConstructor
@Setter
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

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

    @Builder
    public User(String email, String password, Collection<UserAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = EnumSet.copyOf(authorities);
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

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
