package com.example.petproject.util;

import com.example.petproject.dto.response.IdResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@UtilityClass
public class CommonUtils {
    public static IdResponse buildIdResponse(UUID id) {
        return IdResponse.builder().id(id).build();
    }
}