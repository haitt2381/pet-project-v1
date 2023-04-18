package com.example.petproject.service;

import com.example.petproject.config.KeycloakProvider;
import com.example.petproject.dto.request.CreateUserRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class KeycloakAdminClientService {
    final KeycloakProvider kcProvider;

    public String createKeycloakUser(CreateUserRequest user) {
        UsersResource usersResource = kcProvider.getUsersResource();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstname());
        kcUser.setLastName(user.getLastname());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);

        try (Response response = usersResource.create(kcUser)) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            addRealmRoleToUser(userId, user.getRole());
            return userId;
        } catch (Exception ex) {
            log.info(ex.toString());
        }
        return null;
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    public void addRealmRoleToUser(String userId, String roleName){
        // Get realm
        RealmResource realmResource = kcProvider.getRealmResource();
        UserResource userResource = this.getUserResourceById(userId);

        String clientId = kcProvider.getClientRepresentation().getId();

        RoleRepresentation realmRole = realmResource
                .clients()
                .get(clientId)
                .roles()
                .get(roleName)
                .toRepresentation();

        // Assign realm role to user
        userResource.roles().clientLevel(clientId) //
                .add(Collections.singletonList(realmRole));
    }

    public UserResource getUserResourceById(String userId) {
        return kcProvider.getUsersResource().get(userId);
    }

}
