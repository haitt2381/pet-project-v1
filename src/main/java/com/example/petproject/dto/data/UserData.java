package com.example.petproject.dto.data;

import com.example.petproject.constant.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserData {
    String id;
    OffsetDateTime createdAt;
    OffsetDateTime modifiedAt;
    String firstName;
    String lastName;
    String username;
    String email;
    String userKeycloakId;
    String phoneNumber;
    Boolean isActive;
    Boolean isDeleted;
    Role role;
}