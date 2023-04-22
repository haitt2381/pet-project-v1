package com.example.petproject.service;

import com.example.petproject.dto.data.UserData;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.dto.response.GetUsersResponse;
import com.example.petproject.common.dto.IdResponse;

import java.util.UUID;

public interface IUserService {

    GetUsersResponse getUsers(GetUsersRequest request);

    UserData getUserById(UUID id);

    UserData getUserByEmail(String email);

    IdResponse createUser(CreateUserRequest request);
}