package com.example.petproject.common.exception;

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
    INVALID("INVALID", "invalid"),
    UUID_INVALID("UUID_INVALID", "Invalid id, please try again"),


    //Http error
    FORBIDDEN("FORBIDDEN", "User requires permission"),
    BAD_REQUEST("BAD_REQUEST", "Bad Request"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal Server Error"),
    UNAUTHORIZED("UNAUTHORIZED", "Unauthorized"),
    PERMISSION_DENIED("PERMISSION_DENIED", "Permission Denied"),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", "Service Unavailable"),

    //User, keycloak & Authentication
    CREATE_USER_KEYCLOAK_FAILED("CREATE_USER_KEYCLOAK_FAILED", "Create user keycloak failed"),
    UPDATE_USER_KEYCLOAK_FAILED("UPDATE_USER_KEYCLOAK_FAILED", "Update user keycloak failed"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists"),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email already exists"),
    USERNAME_ALREADY_EXISTS("USERNAME_ALREADY_EXISTS", "Username already exists"),
    PHONE_NUMBER_ALREADY_EXISTS("PHONE_NUMBER_ALREADY_EXISTS", "Phone number already exists"),
    DELETE_USER_KEYCLOAK_FAILED("DELETE_USER_KEYCLOAK_FAILED","Delete user keycloak failed"),
    CREATE_USER_FAILED("CREATE_USER_FAILED", "Create user failed"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS","Username or password are incorrect"),
    EMAIL_NOT_VERIFIED("EMAIL_NOT_VERIFIED", "User probably hasn't verified email"),
    USER_NOT_ACTIVE("USER_NOT_ACTIVE", "User probably hasn't active, please check email for active account or contact admin"),
    USER_NOT_FOUND("USER_NOT_FOUND", "user not found"),

    //Size upload file
    MAXIMUM_UPLOAD_SIZE_EXCEEDED("MAXIMUM_UPLOAD_SIZE_EXCEEDED", "Maximum upload size exceeded"),

    ;
    final String code;
    final String message;
}

