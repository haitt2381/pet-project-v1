package com.example.petproject.entity;

import com.example.petproject.util.DateTimeUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public abstract class AuditEntity {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(length = 36, name = "id", updatable = false, nullable = false)
    UUID id;

    @Version
    Long version;

    @Column()
    @CreatedBy
    String createdBy;

    @Column()
    @LastModifiedBy
    String modifiedBy;

    @Column()
    @CreatedDate
    OffsetDateTime createdAt;

    @Column()
    @LastModifiedDate
    OffsetDateTime modifiedAt;

    @PrePersist
    void preInsert() {
        this.createdAt = DateTimeUtils.nowToOffsetDateTime();
        this.modifiedAt = createdAt;
//        this.createdBy = Objects.nonNull(ProfileLocal.getUserId()) ? ProfileLocal.getUserId() : null;
        this.version = 1L;
    }

    @PreUpdate
    void preUpdate() {
        this.modifiedAt = DateTimeUtils.nowToOffsetDateTime();
//        this.modifiedBy = Objects.nonNull(ProfileLocal.getUserId()) ? ProfileLocal.getUserId() : null;
    }
}