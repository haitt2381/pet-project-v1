package com.example.petproject.entity;

import com.example.petproject.common.entity.AuditEntity;
import com.example.petproject.constant.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "app_user")
@SQLDelete(sql = "Update app_user SET is_deleted = true, modified_at = now(), version = version + 1 WHERE id = ? and version = ?")
@DynamicUpdate
public class User extends AuditEntity {

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(unique = true, nullable = false)
    String username;

    @Column(unique = true, nullable = false)
    String email;

    @Column(unique = true, nullable = false)
    String userKeycloakId;

    @Column(unique = true)
    String phoneNumber;

    @Column(nullable = false)
    Boolean isActive = Boolean.FALSE;

    @Column(nullable = false)
    Boolean isDeleted = Boolean.FALSE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;
}
