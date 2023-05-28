package com.example.petproject.service.impl;

import com.example.petproject.common.dto.IdResponse;
import com.example.petproject.common.exception.AppErrorInfo;
import com.example.petproject.common.exception.AppRuntimeException;
import com.example.petproject.common.util.CommonUtils;
import com.example.petproject.common.util.RequestUtils;
import com.example.petproject.dto.data.UserData;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.dto.request.UpdateUserRequest;
import com.example.petproject.dto.response.GetUserResponse;
import com.example.petproject.dto.response.GetUsersResponse;
import com.example.petproject.entity.User;
import com.example.petproject.mapper.UserMapper;
import com.example.petproject.repository.UserRepository;
import com.example.petproject.service.IKeycloakAdminClientService;
import com.example.petproject.service.IUserService;
import com.example.petproject.specification.UserSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class UserService implements IUserService {

    final UserRepository userRepository;

    final IKeycloakAdminClientService kcService;

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
    public GetUserResponse getUser(String id) {
        UUID uuid = CommonUtils.isValidUUID(id);
        User user = this.getUserById(uuid);
        UserData userDto = userMapper.toUserData(user);
        return GetUserResponse.builder().data(userDto).build();
    }

    public UserData getUserByEmailOrUsername(String emailOrUsername) {
        Optional<User> user = userRepository.findByEmailOrUsername(emailOrUsername, emailOrUsername);

        if (user.isEmpty()) {
            log.info("[UserService] Not found user: {}", emailOrUsername);
            throw new AppRuntimeException(AppErrorInfo.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return userMapper.toUserData(user.get());
    }

    private User getUserById(UUID userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            log.info("[UserService] Not found user: {}", userId);
            throw new AppRuntimeException(AppErrorInfo.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public IdResponse createUser(CreateUserRequest request) {
        checkUserExisted(request.getEmail(), request.getUsername(), request.getPhoneNumber());

        String userKeycloakId = kcService.createKeycloakUser(request);
        if (Objects.isNull(userKeycloakId)) {
            throw new AppRuntimeException(AppErrorInfo.CREATE_USER_KEYCLOAK_FAILED);
        }

        User userSaved;
        try {
            User user = userMapper.toUser(request);
            user.setUserKeycloakId(userKeycloakId);
            userSaved = userRepository.save(user);
        } catch (Exception ex) {
            log.info("Fail to create user, trying delete user keycloak", ex);
            kcService.deleteKeycloakUserById(userKeycloakId);
            throw new AppRuntimeException(AppErrorInfo.CREATE_USER_FAILED);
        }

        //TODO: send mail to verify email & active user

        return CommonUtils.buildIdResponse(userSaved.getId());
    }

    @Override
    public void checkUserExisted(String email, String username, String phoneNumber) {
        if (userRepository.existsByEmail(email)) {
            throw new AppRuntimeException(AppErrorInfo.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        if (userRepository.existsByUsername(username)) {
            throw new AppRuntimeException(AppErrorInfo.USERNAME_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new AppRuntimeException(AppErrorInfo.PHONE_NUMBER_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }
    }

    @Override
    public void isActiveUser(String id) {
        UUID uuid = CommonUtils.isValidUUID(id);
        User user = this.getUserById(uuid);

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new AppRuntimeException(AppErrorInfo.USER_NOT_ACTIVE, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public IdResponse activateAndDeactivateUser(String id, boolean isActive) {
        UUID uuid = CommonUtils.isValidUUID(id);
        User user = getUserById(uuid);
        user.setIsActive(isActive);

        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder().isActive(isActive).build();
        kcService.updateKeycloakUser(user.getUserKeycloakId(), updateUserRequest);

        User userSaved = userRepository.save(user);
        return CommonUtils.buildIdResponse(userSaved.getId());
    }

    @Override
    public IdResponse updateUser(UpdateUserRequest request) {
        User user = getUserById(request.getId());
        userMapper.toUser(user, request);
        kcService.updateKeycloakUser(user.getUserKeycloakId(), request);
        User userSaved = userRepository.save(user);

        //TODO: send email notify user if email changed

        return CommonUtils.buildIdResponse(userSaved.getId());
    }

    @Override
    public IdResponse deleteUser(String id) {
        UUID uuid = CommonUtils.isValidUUID(id);
        User user = getUserById(uuid);
        userRepository.delete(user);
        return CommonUtils.buildIdResponse(uuid);
    }
}
