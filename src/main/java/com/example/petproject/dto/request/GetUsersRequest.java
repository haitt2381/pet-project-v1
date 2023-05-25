package com.example.petproject.dto.request;

import com.example.petproject.common.dto.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class GetUsersRequest extends BaseRequest {
    @ApiModelProperty(notes = "Any keyword for search name, username, email, phoneNumber")
    String keyword;
    List<String> role;
    Boolean isActive;
    Boolean isDeleted;
}
