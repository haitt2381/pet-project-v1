package com.example.petproject.common.entity;

import com.example.petproject.common.util.CommonUtils;
import com.example.petproject.common.util.DateTimeUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
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
    LocalDateTime createdAt;

    @Column()
    @LastModifiedDate
    LocalDateTime modifiedAt;

    @PrePersist
    void preInsert() {
        String createdByUser = CommonUtils.getCurrentUsernameLogged();
        this.createdAt = DateTimeUtils.nowToLocalDateTime();
        this.modifiedAt = createdAt;
        this.createdBy = createdByUser;
        this.modifiedBy = createdByUser;
        this.version = 1L;
    }

    @PreUpdate
    void preUpdate() {
        String modifiedByUser = CommonUtils.getCurrentUsernameLogged();
        this.modifiedAt = DateTimeUtils.nowToLocalDateTime();
        this.modifiedBy = modifiedByUser;
    }
}
