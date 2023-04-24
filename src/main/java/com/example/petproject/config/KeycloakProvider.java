package com.example.petproject.config;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Configuration
public class KeycloakProvider {

    @Value("${keycloak.auth-server-url}")
    String serverURL;
    @Value("${keycloak.realm}")
    String realm;
    @Value("${keycloak.resource}")
    String clientID;
    @Value("${keycloak.credentials.secret}")
    String clientSecret;

    static Keycloak keycloak = null;
    static UsersResource usersResource = null;
    static RealmResource realmResource = null;
    static ClientRepresentation clientRepresentation = null;

    public Keycloak getInstance() {
        if (keycloak == null) {

            return KeycloakBuilder.builder()
                    .realm(realm)
                    .serverUrl(serverURL)
                    .clientId(clientID)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();
        }
        return keycloak;
    }

    public UsersResource getUsersResource() {
        if(Objects.isNull(usersResource)) {
            return this.getInstance().realm(realm).users();
        }
        return usersResource;
    }

    public RealmResource getRealmResource() {
        if(Objects.isNull(realmResource)) {
            return this.getInstance().realm(realm);
        }
        return realmResource;
    }

    public ClientRepresentation getClientRepresentation() {
        if (Objects.isNull(clientRepresentation)) {
            return this.getRealmResource()
                    .clients().findByClientId(clientID)
                    .get(0);
        }
        return clientRepresentation;
    }

    public Keycloak newKeycloakBuilderWithPasswordCredentials(String username, String password) {
        return KeycloakBuilder.builder() //
                .realm(realm) //
                .serverUrl(serverURL)//
                .grantType(OAuth2Constants.PASSWORD) //
                .clientId(clientID) //
                .clientSecret(clientSecret) //
                .username(username) //
                .password(password)
                .build();
    }

    public JsonNode refreshToken(String refreshToken) throws UnirestException {
        String url = serverURL + "/realms/" + realm + "/protocol/openid-connect/token";
        return Unirest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("client_id", clientID)
                .field("client_secret", clientSecret)
                .field("refresh_token", refreshToken)
                .field("grant_type", "refresh_token")
                .asJson().getBody();
    }
}