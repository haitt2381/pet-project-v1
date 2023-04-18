package com.example.petproject.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum AppErrorInfo {

    //Common error
    ID_CANNOT_BE_NULL("ID_CANNOT_BE_NULL", "Id can't be null"),
    ENTITY_NOT_FOUND("ENTITY_NOT_FOUND", "Entity not found"),

    //Http error
    FORBIDDEN("FORBIDDEN", "Forbidden"),
    BAD_REQUEST("BAD_REQUEST", "Bad Request"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal Server Error"),
    UNAUTHORIZED("UNAUTHORIZED", "Unauthorized"),
    PERMISSION_DENIED("PERMISSION_DENIED", "Permission Denied"),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", "Service Unavailable"),

    //User & keycloak
    CANNOT_CREATE_USER_KEYCLOAK("CANNOT_CREATE_USER_KEYCLOAK", "Cannot create user keycloak"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists"),

    //Size upload file
    MAXIMUM_UPLOAD_SIZE_EXCEEDED("MAXIMUM_UPLOAD_SIZE_EXCEEDED", "Maximum upload size exceeded"),

    ;
    final String code;
    final String message;
}

