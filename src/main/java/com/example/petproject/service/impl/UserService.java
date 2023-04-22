package com.example.petproject.service.impl;

import com.example.petproject.common.dto.IdResponse;
import com.example.petproject.common.exception.AppErrorInfo;
import com.example.petproject.common.exception.AppRuntimeException;
import com.example.petproject.common.util.CommonUtils;
import com.example.petproject.common.util.RequestUtils;
import com.example.petproject.dto.data.UserData;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.dto.response.GetUsersResponse;
import com.example.petproject.entity.User;
import com.example.petproject.mapper.UserMapper;
import com.example.petproject.repository.UserRepository;
import com.example.petproject.service.IUserService;
import com.example.petproject.specification.UserSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class UserService implements IUserService {

    final UserRepository userRepository;

    final KeycloakAdminClientService kcService;

    final UserMapper userMapper;

    @Override
    public GetUsersResponse getUsers(GetUsersRequest request) {
        Pageable pageRequest = RequestUtils.getPageRequest(request.getRequestInfo());
        Specification<User> specRequest = UserSpecification.buildSpecification(request);
        Page<User> users = userRepository.findAll(specRequest, pageRequest);

        return GetUsersResponse.builder()
                .responseInfo(CommonUtils.toResponseInfo(users))
                .data(userMapper.toListUserData(users.getContent()))
                .build();
    }

    @Override
    public UserData getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toUserData).orElse(null);
    }

    @Override
    public UserData getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMapper::toUserData).orElse(null);
    }

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