package com.example.petproject.mapper;

import com.example.petproject.common.mapper.CommonMapper;
import com.example.petproject.dto.data.UserData;
import com.example.petproject.dto.request.CreateUserRequest;
import com.example.petproject.entity.User;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommonMapper.class, UserMapper.class}
)
public interface UserMapper {
    User toUser(CreateUserRequest request);

    UserData toUserData(User user);

    List<UserData> toListUserData(List<User> users);
}
