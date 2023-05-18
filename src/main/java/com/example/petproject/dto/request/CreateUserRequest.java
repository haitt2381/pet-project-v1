package com.example.petproject.dto.request;

import com.example.petproject.common.annotation.ValueOfEnum;
import com.example.petproject.constant.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CreateUserRequest {

    // RFC 5322 for Email Validation
    @ApiModelProperty(example = "admin@gmail.com", required = true)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "invalid")
    String email;

    @ApiModelProperty(example = "admin", required = true)
    @NotBlank
    String firstName;

    @ApiModelProperty(example = "admin", required = true)
    @NotBlank
    String lastName;

    @ApiModelProperty(example = "ADMIN", required = true)
    @ValueOfEnum(enumClass = Role.class, message = "must be any of ADMIN, MODERATOR, MEMBER")
    String role;

    @ApiModelProperty(example = "0913643812", required = true)
    @Pattern(regexp = "^\\d{10}$", message = "invalid")
    String phoneNumber;

    @ApiModelProperty(example = "admin", required = true)
    @Length(min = 2, max = 32)
    String username;

    @ApiModelProperty(example = "Password123@", required = true)
    @Length(min = 2, max = 64)
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "invalid")
    String password;

     /*
    ^                 # start-of-string
    (?=.*\\d)       # a digit must occur at least once
    (?=.*[a-z])       # a lower case letter must occur at least once
    (?=.*[A-Z])       # an upper case letter must occur at least once
    (?=.*[@#$%^&+=])  # a special character must occur at least once
    (?=\S+$)          # no whitespace allowed in the entire string
    .{8,}             # anything, at least eight places though
    $                 # end-of-string
    * */
}