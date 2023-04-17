package com.example.petproject.service;

import com.example.petproject.config.KeycloakProvider;
import com.example.petproject.dto.CreateUserRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class KeycloakAdminClientService {
    final KeycloakProvider kcProvider;

    public KeycloakAdminClientService(KeycloakProvider keycloakProvider) {
        this.kcProvider = keycloakProvider;
    }

    public int createKeycloakUser(CreateUserRequest user) {
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

        Response response = usersResource.create(kcUser);

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            addRealmRoleToUser(userId, user.getRoleName());

            //If you want to save the user to your other database, do it here, for example:
//            User localUser = new User();
//            localUser.setFirstName(kcUser.getFirstName());
//            localUser.setLastName(kcUser.getLastName());
//            localUser.setEmail(user.getEmail());
//            localUser.setCreatedDate(Timestamp.from(Instant.now()));
//            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//            usersResource.get(userId).sendVerifyEmail();
//            userRepository.save(localUser);
        }

        return response.getStatus();

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
