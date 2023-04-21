package com.example.petproject.common.mapper;

import com.example.petproject.common.dto.RequestInfo;
import com.example.petproject.common.dto.ResponseInfo;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommonMapper {

    ResponseInfo toResponseInfo(RequestInfo requestInfo);
}