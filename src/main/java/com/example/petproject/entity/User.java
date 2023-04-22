package com.example.petproject.entity;

import com.example.petproject.common.entity.AuditEntity;
import com.example.petproject.constant.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "app_user")
@SQLDelete(sql = "Update app_user SET is_deleted = true, modified_at = now(), version = version + 1 WHERE id = ? and version = ?")
@Where(clause = "is_deleted = false")
public class User extends AuditEntity {

    @Column(nullable = false)
    String firstname;

    @Column(nullable = false)
    String lastname;

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
