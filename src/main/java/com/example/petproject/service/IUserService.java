package com.example.petproject.service;

import com.example.petproject.common.dto.IdResponse;
import com.example.petproject.dto.data.UserData;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.dto.request.UpdateUserRequest;
import com.example.petproject.dto.response.GetUserResponse;
import com.example.petproject.dto.response.GetUsersResponse;

public interface IUserService {

    GetUsersResponse getUsers(GetUsersRequest request);

    GetUserResponse getUser(String id);

    UserData getUserByEmailOrUsername(String emailOrUsername);

    IdResponse createUser(CreateUserRequest request);

    void checkUserExisted(String email, String username, String phoneNumber);

    void isActiveUser(String emailOrUsername);

    IdResponse activateAndDeactivateUser(String emailOrUsername, boolean isActive);

    IdResponse updateUser(UpdateUserRequest request);

    IdResponse deleteUser(String id);

    IdResponse hardDeleteUser(String id);

    IdResponse restoreUser(String id);
}
