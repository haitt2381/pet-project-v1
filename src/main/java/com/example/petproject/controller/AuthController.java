package com.example.petproject.controller;

import com.example.petproject.dto.request.LoginRequest;
import com.example.petproject.service.IAuthenticationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@Api(tags = "Auth")
@RestController
@RequestMapping("auth")
public class AuthController {

    final IAuthenticationService authService;

    @PostMapping("/login")
    public AccessTokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping(path = "/logout")
    public void logout(HttpServletRequest request) throws ServletException {
        authService.logout();
        request.logout();
    }
}
