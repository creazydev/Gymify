package com.github.Gymify.persistence.auditing;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class AuditedEntity {

    @Column(name = "creation_timestamp")
    @CreationTimestamp
    private Timestamp creationTimestamp;

    @Column(name = "update_timestamp")
    @UpdateTimestamp
    private Timestamp updateTimestamp;

    @Column(name = "created_by_user_id")
    @CreatedBy
    private Long createdByUserId;

    @Column(name = "modified_by_user_id")
    @LastModifiedBy
    private Long modifiedByUserId;
}
