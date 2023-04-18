package com.example.petproject.service.impl;

import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.response.IdResponse;
import com.example.petproject.entity.User;
import com.example.petproject.mapper.UserMapper;
import com.example.petproject.service.IUserService;
import com.example.petproject.service.KeycloakAdminClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class UserService implements IUserService {

    final KeycloakAdminClientService kcService;
    final UserMapper userMapper;

    @Override
    public IdResponse createUser(CreateUserRequest request) {
        String userKeycloakId = kcService.createKeycloakUser(request);
        if (Objects.isNull(userKeycloakId)) {
            //TODO: throw error
        }

        User user = userMapper.toUser(request);
        user.setUserKeycloakId(userKeycloakId);

        //TODO: send mail to verify email & active user

        return null;
    }
}