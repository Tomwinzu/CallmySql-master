package com.userManagement.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ApiException> handleEntityNotFoundException(EntityNotFoundException ex) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        var entityNotFoundException = new ApiException(
                unauthorized,
                ex.getMessage()
        );
        return new ResponseEntity<>(entityNotFoundException, unauthorized);
    }

}
