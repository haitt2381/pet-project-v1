package com.example.petproject.service;

import com.example.petproject.dto.data.UserData;
import com.example.petproject.dto.request.LoginRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface IAuthenticationService {

    UserData getCurrentUser();

    AccessTokenResponse login(LoginRequest request);

    void logout();

}