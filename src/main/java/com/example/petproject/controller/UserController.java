package com.example.petproject.controller;

import com.example.petproject.common.dto.IdResponse;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.dto.response.GetUsersResponse;
import com.example.petproject.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Api( tags = "Users")
@RestController
@RequestMapping("/user")
public class UserController {

    final IUserService userService;

    @ApiOperation(value = "Get list user")
    @RolesAllowed("ROLE_moderator")
    @PostMapping("/list")
    public GetUsersResponse getUser(@RequestBody @ApiParam("Request body") GetUsersRequest request) {
        return userService.getUsers(request);
    }

    @ApiOperation(value = "Create user")
    @RolesAllowed("ROLE_admin")
    @PostMapping(value = "/create")
    public IdResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}