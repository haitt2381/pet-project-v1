package com.example.petproject.controller;

import com.example.petproject.common.dto.IdResponse;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.dto.request.UpdateUserRequest;
import com.example.petproject.dto.response.GetUserResponse;
import com.example.petproject.dto.response.GetUsersResponse;
import com.example.petproject.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Api(tags = "Users")
@RestController
@RequestMapping("/api/user")
public class UserController {

    final IUserService userService;

    @ApiOperation(value = "Get list user")
    @RolesAllowed("ROLE_moderator")
    @PostMapping("/list")
    public GetUsersResponse getUsers(@RequestBody @ApiParam("Request body") GetUsersRequest request) {
        return userService.getUsers(request);
    }

    @ApiOperation(value = "Get user detail")
    @RolesAllowed("ROLE_admin")
    @GetMapping("/{id}")
    public GetUserResponse getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @ApiOperation(value = "Create user")
//    @RolesAllowed("ROLE_admin")
    @PostMapping(value = "/create")
    public IdResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @ApiOperation(value = "Activate user")
    @RolesAllowed("ROLE_admin")
    @GetMapping(value = "/activate/{id}")
    public IdResponse activateUser(@PathVariable String id) {
        return userService.activateAndDeactivateUser(id, true);
    }

    @ApiOperation(value = "Deactivate user")
    @RolesAllowed("ROLE_admin")
    @GetMapping(value = "/deactivate/{id}")
    public IdResponse deactivateUser(@PathVariable String id) {
        return userService.activateAndDeactivateUser(id, false);
    }

    @ApiOperation(value = "Update user")
    @RolesAllowed("ROLE_admin")
    @PutMapping(value = "/update")
    public IdResponse updateUser(@Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(request);
    }

    @DeleteMapping("/{id}")
    public IdResponse deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

}
