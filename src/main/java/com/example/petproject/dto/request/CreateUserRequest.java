package com.example.petproject.dto.request;

import com.example.petproject.annotation.ValueOfEnum;
import com.example.petproject.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank
    @Length(min = 2, max = 32)
    String username;

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
    @NotBlank
    @Length(min = 8)
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")
    String password;

    // RFC 5322 for Email Validation
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    String email;

    @NotBlank
    String firstname;

    @NotBlank
    String lastname;

    @NotBlank
    @ValueOfEnum(enumClass = Role.class)
    String role;

    @Pattern(regexp = "^\\d{10}$")
    String phoneNumber;
}