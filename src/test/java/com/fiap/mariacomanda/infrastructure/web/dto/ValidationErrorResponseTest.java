package com.fiap.mariacomanda.infrastructure.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ValidationErrorResponseTest {

    @Test
    @DisplayName("Deve criar ValidationErrorResponse com lista de FieldErrors corretamente")
    void deveCriarValidationErrorResponseComFieldErrors() {
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 400;
        String error = "Bad Request";
        String message = "Validation failed";
        String path = "/api/users";

        ValidationErrorResponse.FieldError fieldError1 = new ValidationErrorResponse.FieldError("name", "must not be blank");
        ValidationErrorResponse.FieldError fieldError2 = new ValidationErrorResponse.FieldError("email", "must be a valid email");

        ValidationErrorResponse response = new ValidationErrorResponse(
                timestamp,
                status,
                error,
                message,
                path,
                List.of(fieldError1, fieldError2)
        );

        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
        assertNotNull(response.getFieldErrors());
        assertEquals(2, response.getFieldErrors().size());
        assertEquals("name", response.getFieldErrors().get(0).getField());
        assertEquals("must not be blank", response.getFieldErrors().get(0).getMessage());
        assertEquals("email", response.getFieldErrors().get(1).getField());
        assertEquals("must be a valid email", response.getFieldErrors().get(1).getMessage());
    }
}
