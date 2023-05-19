package com.example.petproject.mapper;

import com.example.petproject.common.mapper.CommonMapper;
import com.example.petproject.dto.data.UserData;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.dto.request.UpdateUserRequest;
import com.example.petproject.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommonMapper.class, UserMapper.class}
)
public interface UserMapper {
    User toUser(CreateUserRequest createUserRequest);

    UserData toUserData(User user);

    List<UserData> toListUserData(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", source = "isActive")
    void updateUserRepresentation(@MappingTarget UserRepresentation userRepresentation, UpdateUserRequest updateUserRequest);

    void toUser(@MappingTarget User user, UpdateUserRequest updateUserRequest);
}
