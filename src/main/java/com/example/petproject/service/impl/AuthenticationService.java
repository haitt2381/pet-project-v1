package com.example.petproject.service.impl;

import com.example.petproject.common.exception.AppErrorInfo;
import com.example.petproject.common.exception.AppRuntimeException;
import com.example.petproject.config.KeycloakProvider;
import com.example.petproject.dto.data.UserData;
import com.example.petproject.dto.request.LoginRequest;
import com.example.petproject.service.IAuthenticationService;
import com.example.petproject.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService {

    final HttpServletRequest httpRequest;
    final KeycloakProvider kcProvider;

    final IUserService userService;

    @Override
    public UserData getCurrentUser() {
        Principal userPrincipal = httpRequest.getUserPrincipal();
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) userPrincipal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        return userService.getUserByEmail(accessToken.getEmail());
    }

    @Override
    public AccessTokenResponse login(LoginRequest request) {
        try(Keycloak keycloak =
                    kcProvider.newKeycloakBuilderWithPasswordCredentials(request.getUsername(), request.getPassword())) {
            return keycloak.tokenManager().getAccessToken();
        } catch (NotAuthorizedException ex) {
            log.warn("Invalid account", ex);
            throw new AppRuntimeException(AppErrorInfo.INVALID_CREDENTIALS);
        } catch (BadRequestException ex) {
            log.warn("invalid account. User probably hasn't verified email.", ex);
            throw new AppRuntimeException(AppErrorInfo.FORBIDDEN);
        }

    }

    public void logout() {
        UserData currentUser = getCurrentUser();
        UserResource userResource = kcProvider.getUsersResource().get(currentUser.getUserKeycloakId());
        userResource.logout();
    }


}