package com.example.petproject.common.exception;

import com.example.petproject.common.dto.MessageInfo;
import com.example.petproject.common.dto.ResponseInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    final MessageInfo error;
    final HttpStatus status;
    final List<MessageInfo> errors;

    public AppRuntimeException(AppErrorInfo error) {
        this.error = MessageInfo.builder().code(error.getCode()).message(error.getMessage()).build();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errors = null;
    }

    public AppRuntimeException(AppErrorInfo error, HttpStatus status) {
        this.error = MessageInfo.builder().code(error.getCode()).message(error.getMessage()).build();
        this.status = status;
        this.errors = null;
    }

    public AppRuntimeException(String code, String message) {
        this.error = MessageInfo.builder().code(code).message(message).build();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errors = null;
    }

    public AppRuntimeException(List<AppErrorInfo> errors, HttpStatus status) {
        this.errors = errors.stream()
                .map(errorInfo -> MessageInfo.builder().code(errorInfo.getCode()).message(errorInfo.getMessage()).build())
                .collect(Collectors.toList());
        this.status = status;
        this.error = null;
    }

    public ResponseInfo toResponseInfo() {
        if(Objects.nonNull(this.error)) {
            return ResponseInfo.builder().errors(Collections.singletonList(this.error)).build();
        }
        return ResponseInfo.builder().errors(this.errors).build();
    }
}
