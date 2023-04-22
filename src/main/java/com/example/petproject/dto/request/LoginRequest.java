package com.example.petproject.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @ApiModelProperty(example = "admin", required = true)
    @NotBlank(message = "Username cannot be blank")
    String username;

    @ApiModelProperty(example = "admin", required = true)
    @NotBlank
    String password;
}