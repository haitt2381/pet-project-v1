package com.example.petproject.common.exception;

import com.example.petproject.common.dto.MessageInfo;
import com.example.petproject.common.dto.ResponseInfo;
import com.example.petproject.common.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("MethodArgumentNotValidException", ex);
        List<MessageInfo> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> MessageInfo.builder().code(error.getField()).message(error.getDefaultMessage()).build())
                .collect(Collectors.toList());
        ResponseInfo responseInfo = ResponseInfo.builder().errors(errors).build();
        return new ResponseEntity<>(BaseResponse.builder().responseInfo(responseInfo).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppRuntimeException.class)
    public ResponseEntity<Object> handle(AppRuntimeException ex) {
        logger.error("AppRuntimeException");
        BaseResponse response = BaseResponse.builder().responseInfo(ex.toResponseInfo()).build();
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handle(MaxUploadSizeExceededException ex) {
        logger.error("MaxUploadSizeExceededException", ex);
        MessageInfo error = this.buildMessageInfo(AppErrorInfo.MAXIMUM_UPLOAD_SIZE_EXCEEDED);
        return buildErrorResponse(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handle(AccessDeniedException ex) {
        logger.error("AccessDeniedException", ex);
        MessageInfo error = this.buildMessageInfo(AppErrorInfo.FORBIDDEN);
        return buildErrorResponse(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handle(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException", ex);
        MessageInfo error = this.buildMessageInfo(AppErrorInfo.BAD_REQUEST);
        return buildErrorResponse(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex) {
        logger.error("InternalServerError", ex);
        MessageInfo error = this.buildMessageInfo(AppErrorInfo.INTERNAL_SERVER_ERROR);
        return buildErrorResponse(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(MessageInfo message, HttpStatus status) {
        ResponseInfo responseInfo = ResponseInfo.builder().errors(Collections.singletonList(message)).build();
        return new ResponseEntity<>(BaseResponse.builder().responseInfo(responseInfo).build(), status);
    }

    private MessageInfo buildMessageInfo(AppErrorInfo errorInfo) {
        return MessageInfo.builder()
                .code(errorInfo.getCode())
                .message(errorInfo.getMessage()).build();
    }
}