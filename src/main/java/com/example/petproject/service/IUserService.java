package com.example.petproject.service;

import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.dto.response.GetUsersResponse;
import com.example.petproject.common.dto.IdResponse;

public interface IUserService {

    GetUsersResponse getUsers(GetUsersRequest request);

    IdResponse createUser(CreateUserRequest request);
}