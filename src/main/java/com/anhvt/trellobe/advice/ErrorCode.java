package com.anhvt.trellobe.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_EXISTED(HttpStatus.OK, "User existed"),
    USERNAME_INVALID(HttpStatus.OK, "Username is invalid"),
    PASSWORD_INVALID(HttpStatus.OK, "Password is invalid"),
    USER_NOT_EXISTED(HttpStatus.OK, "User not existed"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Unauthorize"),
    ;


    private final HttpStatus status;
    private final String message;
}
