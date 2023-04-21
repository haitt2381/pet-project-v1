package com.example.petproject.common.exception;

import com.example.petproject.common.dto.MessageInfo;
import com.example.petproject.common.dto.ResponseInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.Collections;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    final MessageInfo error;
    final HttpStatus status;

    public AppRuntimeException(AppErrorInfo error) {
        this.error = MessageInfo.builder().code(error.getCode()).message(error.getMessage()).build();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public AppRuntimeException(AppErrorInfo error, HttpStatus status) {
        this.error = MessageInfo.builder().code(error.getCode()).message(error.getMessage()).build();
        this.status = status;
    }

    public AppRuntimeException(String code, String message) {
        this.error = MessageInfo.builder().code(code).message(message).build();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ResponseInfo toResponseInfo() {
        return ResponseInfo.builder().errors(Collections.singletonList(this.error)).build();
    }
}