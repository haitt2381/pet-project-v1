package com.example.petproject.service.impl;

import com.example.petproject.dto.data.UserData;
import com.example.petproject.service.IAuthenticationService;
import com.example.petproject.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService {

    final HttpServletRequest httpRequest;

    final IUserService userService;

    @Override
    public UserData getCurrentUser() {
        Principal userPrincipal = httpRequest.getUserPrincipal();
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) userPrincipal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        return userService.getUserByEmail(accessToken.getEmail());
    }
}