package com.example.petproject.common.util;

import com.example.petproject.common.dto.IdResponse;
import com.example.petproject.common.dto.ResponseInfo;
import com.example.petproject.common.exception.AppErrorInfo;
import com.example.petproject.common.exception.AppRuntimeException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Slf4j
@UtilityClass
public class CommonUtils {
    public static IdResponse buildIdResponse(UUID id) {
        return IdResponse.builder().id(id).build();
    }

    public static <T> ResponseInfo toResponseInfo(Page<T> page) {
        return ResponseInfo.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .total(page.getTotalElements())
                .build();
    }

    public static UUID isValidUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception ex) {
            throw new AppRuntimeException(AppErrorInfo.UUID_INVALID);
        }
    }
}
