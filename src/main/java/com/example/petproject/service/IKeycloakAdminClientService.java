package com.example.petproject.service;

import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.UpdateUserRequest;
import org.keycloak.admin.client.resource.UserResource;

public interface IKeycloakAdminClientService {

    String createKeycloakUser(CreateUserRequest user);

    UserResource getUserResourceById(String userId);

    void updateKeycloakUser(String keycloakId, UpdateUserRequest updateUserRequest);

    void deleteKeycloakUserById(String keycloakId);
}
