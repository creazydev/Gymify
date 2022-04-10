package com.github.Gymify.persistence.entity;

import com.github.Gymify.persistence.auditing.AuditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass

@Getter
@Setter
public abstract class UserResource extends AuditedEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    protected User user;
}
