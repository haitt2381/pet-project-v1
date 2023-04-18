package com.example.petproject.entity;

import com.example.petproject.constant.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

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
@Table
public class User extends AuditEntity{

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
