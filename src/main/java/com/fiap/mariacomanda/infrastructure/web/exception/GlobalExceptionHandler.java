package com.fiap.mariacomanda.infrastructure.web.exception;

import com.fiap.mariacomanda.core.domain.exception.BusinessException;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.exception.UnauthorizedException;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import com.fiap.mariacomanda.infrastructure.web.dto.ErrorResponse;
import com.fiap.mariacomanda.infrastructure.web.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        log.warn("Entity not found: {}", ex.getMessage());
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex, HttpServletRequest request) {
        log.warn("Validation error: {}", ex.getMessage());
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
        log.warn("Unauthorized access: {}", ex.getMessage());
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("Validation failed: {}", ex.getMessage());
        
        List<ValidationErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationErrorResponse.FieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        
        ValidationErrorResponse error = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                request.getRequestURI(),
                fieldErrors
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.warn("Business error: {}", ex.getMessage());
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("JSON parse error: {}", ex.getMessage());
        
        String message = "Invalid JSON format";
        String errorMsg = ex.getMessage();
        
        if (errorMsg.contains("UUID")) {
            String invalidValue = "";
            String fieldName = "";
            
            // Extrair o valor inválido
            if (errorMsg.contains("from String \"")) {
                int start = errorMsg.indexOf("from String \"") + 13;
                int end = errorMsg.indexOf("\"", start);
                if (end > start) {
                    invalidValue = errorMsg.substring(start, end);
                }
            }
            
            // Buscar em toda a cadeia de exceções
            Throwable current = ex;
            while (current != null && fieldName.isEmpty()) {
                String currentMsg = current.getMessage();
                if (currentMsg != null && currentMsg.contains("through reference chain:")) {
                    String chain = currentMsg.substring(currentMsg.indexOf("through reference chain:") + 24);
                    int lastBracket = chain.lastIndexOf('[');
                    if (lastBracket != -1) {
                        int start = chain.indexOf('"', lastBracket) + 1;
                        int end = chain.indexOf('"', start);
                        if (end > start) {
                            fieldName = chain.substring(start, end);
                        }
                    }
                }
                current = current.getCause();
            }
            
            // Se não encontrou o campo, assumir baseado no contexto da URL
            if (fieldName.isEmpty()) {
                if (request.getRequestURI().contains("/restaurants")) {
                    fieldName = "ownerUserId";
                } else {
                    fieldName = "UUID field";
                }
            }
            
            if (!invalidValue.isEmpty()) {
                message = String.format("Invalid value '%s' for field '%s'. Expected a valid UUID format (e.g., 550e8400-e29b-41d4-a716-446655440000)", 
                        invalidValue, fieldName);
            } else {
                message = String.format("Invalid UUID format for field '%s'. Expected a valid UUID (e.g., 550e8400-e29b-41d4-a716-446655440000)", fieldName);
            }
        } else if (errorMsg.contains("Cannot deserialize")) {
            // Verificar se é erro de boolean
            if (errorMsg.contains("Boolean") || errorMsg.contains("dineInOnly")) {
                String fieldName = "dineInOnly";
                String invalidValue = "";
                
                // Extrair o valor inválido
                if (errorMsg.contains("from String \"")) {
                    int start = errorMsg.indexOf("from String \"") + 13;
                    int end = errorMsg.indexOf("\"", start);
                    if (end > start) {
                        invalidValue = errorMsg.substring(start, end);
                    }
                }
                
                if (!invalidValue.isEmpty()) {
                    message = String.format("Invalid value '%s' for field '%s'. Expected a boolean value (true or false)", 
                            invalidValue, fieldName);
                } else {
                    message = String.format("Invalid value for field '%s'. Expected a boolean value (true or false)", fieldName);
                }
            } else {
                message = "Invalid data format in request body";
            }
        }
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.warn("Type mismatch: {}", ex.getMessage());
        
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", 
                ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("Invalid argument: {}", ex.getMessage());
        
        // Se a mensagem contém "not found", tratar como 404
        if (ex.getMessage().toLowerCase().contains("not found")) {
            ErrorResponse error = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    ex.getMessage(),
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Invalid parameter format: " + ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        log.warn("Permission denied: {}", ex.getMessage());
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("Data integrity violation: {}", ex.getMessage());
        
        String message = "Cannot delete resource because it is referenced by other data";
        if (ex.getMessage().contains("MENU_ITEM")) {
            message = "Cannot delete restaurant because it has menu items. Delete menu items first";
        }
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error - Type: {}, Message: {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occurred: " + ex.getClass().getSimpleName() + " - " + ex.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}