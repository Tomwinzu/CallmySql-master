package com.userManagement.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException {


    private final HttpStatus httpStatus;
    private LocalDateTime localDateTime ;
    private final String message;

    public ApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime.now();
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getMessage() {
        return message;
    }
}
