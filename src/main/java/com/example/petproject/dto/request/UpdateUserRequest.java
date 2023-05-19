package com.example.petproject.dto.request;

import com.example.petproject.common.annotation.ValueOfEnum;
import com.example.petproject.constant.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UpdateUserRequest {

    @ApiModelProperty(example = "6e578f8d-5edd-4db1-826c-cf2597a5fcb2", required = true)
//    @NotBlank
//    @Length(min = 36, max = 36)
    UUID id;

    // RFC 5322 for Email Validation
    @ApiModelProperty(example = "admin@gmail.com")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "invalid")
    String email;

    @ApiModelProperty(example = "admin")
    String firstName;

    @ApiModelProperty(example = "admin")
    String lastName;

    @ApiModelProperty(example = "ADMIN")
    @ValueOfEnum(enumClass = Role.class, message = "must be any of ADMIN, MODERATOR, MEMBER")
    String role;

    @ApiModelProperty(example = "0913643812")
    @Pattern(regexp = "^\\d{10}$", message = "invalid")
    String phoneNumber;

    @ApiModelProperty(example = "true")
    Boolean isActive;
}
