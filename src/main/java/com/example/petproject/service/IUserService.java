package com.example.petproject.service;

import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.response.IdResponse;

public interface IUserService {
    IdResponse createUser(CreateUserRequest request);
}