package com.example.petproject.service.impl;

import com.example.petproject.util.CommonUtils;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.response.IdResponse;
import com.example.petproject.entity.User;
import com.example.petproject.exception.AppErrorInfo;
import com.example.petproject.exception.AppRuntimeException;
import com.example.petproject.mapper.UserMapper;
import com.example.petproject.repository.UserRepository;
import com.example.petproject.service.IUserService;
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
    final UserRepository userRepository;

    @Override
    public IdResponse createUser(CreateUserRequest request) {
        String userKeycloakId = kcService.createKeycloakUser(request);
        if (Objects.isNull(userKeycloakId)) {
            throw new AppRuntimeException(AppErrorInfo.CANNOT_CREATE_USER_KEYCLOAK);
        }

        User user = userMapper.toUser(request);
        user.setUserKeycloakId(userKeycloakId);

        User userSaved = userRepository.save(user);
        //TODO: send mail to verify email & active user

        return CommonUtils.buildIdResponse(userSaved.getId());
    }
}