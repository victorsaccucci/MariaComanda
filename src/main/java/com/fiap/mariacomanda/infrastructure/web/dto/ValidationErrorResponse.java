package com.fiap.mariacomanda.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Validation error response structure")
public class ValidationErrorResponse extends ErrorResponse {

    @Schema(description = "List of validation errors")
    private List<FieldError> fieldErrors;

    public ValidationErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, List<FieldError> fieldErrors) {
        super(timestamp, status, error, message, path);
        this.fieldErrors = fieldErrors;
    }

    @Data
    @Schema(description = "Field validation error")
    public static class FieldError {
        @Schema(description = "Field name", example = "name")
        private String field;

        @Schema(description = "Error message", example = "must not be blank")
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}