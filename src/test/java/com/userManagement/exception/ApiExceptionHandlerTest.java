package com.userManagement.exception;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ApiExceptionHandlerTest {


    @Test
    void handleEntityNotFoundException() {

        var handler = new ApiExceptionHandler();
            var responseEntity = handler.handleEntityNotFoundException(new EntityNotFoundException());
            assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        }
    }