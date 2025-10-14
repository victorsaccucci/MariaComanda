package com.fiap.mariacomanda.infrastructure.web.exception;

import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.exception.UnauthorizedException;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import com.fiap.mariacomanda.infrastructure.web.dto.ErrorResponse;
import com.fiap.mariacomanda.infrastructure.web.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.core.MethodParameter;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
    }

    @Test
    void handleGenericException_deveRetornarErrorResponseComStatus500() {
        Exception ex = new Exception("Erro inesperado de teste");
        String requestUri = "/api/test";
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse.getTimestamp());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorResponse.getError());
        assertTrue(errorResponse.getMessage().contains("An unexpected error occurred: Exception - Erro inesperado de teste"));
        assertEquals(requestUri, errorResponse.getPath());
    }

    @Test
    void handleEntityNotFound_deveRetornarNotFound() {
        EntityNotFoundException ex = new EntityNotFoundException("MenuItem", "xpto-777");
        String path = "/api/menu-items/xpto-777";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleEntityNotFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse error = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), error.getError());
        assertEquals("MenuItem not found with identifier: xpto-777", error.getMessage());
        assertEquals(path, error.getPath());
    }

    @Test
    void handleValidation_deveRetornarBadRequest() {
        ValidationException ex = new ValidationException("Campo nome é obrigatório");
        String path = "/api/users";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleValidation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse error = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), error.getError());
        assertEquals("Campo nome é obrigatório", error.getMessage());
        assertEquals(path, error.getPath());
    }

    @Test
    void handleUnauthorized_deveRetornarForbidden() {
        UnauthorizedException ex = new UnauthorizedException("Usuário não permitido");
        String path = "/api/admin";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleUnauthorized(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse error = response.getBody();
        assertEquals(HttpStatus.FORBIDDEN.value(), error.getStatus());
        assertEquals(HttpStatus.FORBIDDEN.getReasonPhrase(), error.getError());
        assertEquals("Usuário não permitido", error.getMessage());
        assertEquals(path, error.getPath());
    }

    @Test
    void handleMethodArgumentNotValid_deveRetornarValidationErrorResponse() throws Exception {
        Method method = GlobalExceptionHandlerTest.class.getDeclaredMethod("dummyMethod", String.class);
        MethodParameter parameter = new MethodParameter(method, 0);
        
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("user", "name", "Nome é obrigatório");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);
        String path = "/api/users";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ValidationErrorResponse> response = handler.handleMethodArgumentNotValid(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ValidationErrorResponse error = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Validation failed", error.getMessage());
        assertEquals(1, error.getFieldErrors().size());
        assertEquals("name", error.getFieldErrors().get(0).getField());
        assertEquals("Nome é obrigatório", error.getFieldErrors().get(0).getMessage());
    }

    @Test
    void handleTypeMismatch_deveRetornarBadRequest() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getValue()).thenReturn("abc");
        when(ex.getName()).thenReturn("userId");
        when(ex.getRequiredType()).thenReturn((Class) Long.class);
        when(ex.getMessage()).thenReturn("Method parameter 'userId': Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; erro de tipo");
        
        String path = "/api/users/abc";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse error = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals("Invalid value 'abc' for parameter 'userId'. Expected type: Long", error.getMessage());
        assertEquals(path, error.getPath());
    }

    @Test
    void handleDataIntegrityViolation_deleteReferenciadoPorMenuItem() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Constraint violation... MENU_ITEM ...");
        when(request.getMethod()).thenReturn("DELETE");
        when(request.getRequestURI()).thenReturn("/api/restaurants/1");

        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Cannot delete restaurant because it has menu items. Delete menu items first", response.getBody().getMessage());
    }

    @Test
    void handleDataIntegrityViolation_duplicateEntry() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Duplicate entry 'abc@teste.com' for key 'email'");
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Resource already exists with the provided data", response.getBody().getMessage());
        assertEquals("/api/users", response.getBody().getPath());
    }

    @Test
    void handleDataIntegrityViolation_duplicateKey() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("duplicate key value violates unique constraint");
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/api/restaurants");

        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Resource already exists with the provided data", response.getBody().getMessage());
        assertEquals("/api/restaurants", response.getBody().getPath());
    }

    @Test
    void handleDataIntegrityViolation_constraintGeneric() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Check constraint failed for field");
        when(request.getMethod()).thenReturn("PUT");
        when(request.getRequestURI()).thenReturn("/api/items/867");

        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Data integrity constraint violation", response.getBody().getMessage());
        assertEquals("/api/items/867", response.getBody().getPath());
    }

    @Test
    void handleDataIntegrityViolation_deleteComReferencia() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Existe um pedido vinculado a este restaurante.");
        when(request.getMethod()).thenReturn("DELETE");
        when(request.getRequestURI()).thenReturn("/api/restaurants/1");

        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Cannot delete resource because it is referenced by other data", response.getBody().getMessage());
    }

    @Test
    void handleIllegalState_deveRetornarForbiddenComMensagem() {
        IllegalStateException ex = new IllegalStateException("Usuário sem permissão para a ação");
        String path = "/api/some/protected";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleIllegalState(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse body = response.getBody();
        assertEquals(HttpStatus.FORBIDDEN.value(), body.getStatus());
        assertEquals(HttpStatus.FORBIDDEN.getReasonPhrase(), body.getError());
        assertEquals("Usuário sem permissão para a ação", body.getMessage());
        assertEquals(path, body.getPath());
    }

    @Test
    void handleIllegalArgument_deveRetornarNotFoundQuandoMensagemContemNotFound() {
        IllegalArgumentException ex = new IllegalArgumentException("User not found for id: 555");
        String path = "/api/users/555";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgument(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse error = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), error.getError());
        assertEquals("User not found for id: 555", error.getMessage());
        assertEquals(path, error.getPath());
    }

    @Test
    void handleIllegalArgument_deveRetornarBadRequestQuandoMensagemNaoContemNotFound() {
        IllegalArgumentException ex = new IllegalArgumentException("Parametro price deve ser positivo");
        String path = "/api/products";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgument(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse error = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), error.getError());
        assertEquals("Invalid parameter format: Parametro price deve ser positivo", error.getMessage());
        assertEquals(path, error.getPath());
    }

    @Test
    void handleHttpMessageNotReadable_uuidError_comValorInvalido() {
        String invalidUuid = "invalid-uuid-123";
        String errorMessage = String.format("Cannot deserialize value from String \"%s\": not a valid UUID", invalidUuid);
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/restaurants");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid value 'invalid-uuid-123' for field 'ownerUserId'"));
        assertTrue(response.getBody().getMessage().contains("Expected a valid UUID format"));
    }

    @Test
    void handleHttpMessageNotReadable_uuidError_semValorInvalido() {
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'UUID field'"));
    }

    @Test
    void handleHttpMessageNotReadable_booleanError_comValorInvalido() {
        String invalidBoolean = "maybe";
        String errorMessage = String.format("Cannot deserialize Boolean from String \"%s\"", invalidBoolean);
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/menu-items");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid value 'maybe' for field 'dineInOnly'"));
        assertTrue(response.getBody().getMessage().contains("Expected a boolean value (true or false)"));
    }

    @Test
    void handleHttpMessageNotReadable_booleanError_dineInOnly() {
        String errorMessage = "Cannot deserialize dineInOnly field";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/menu-items");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid value for field 'dineInOnly'"));
    }

    @Test
    void handleHttpMessageNotReadable_erroGenerico() {
        String errorMessage = "Malformed JSON request";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/test");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid JSON format", response.getBody().getMessage());
    }

    @Test
    void handleHttpMessageNotReadable_cannotDeserialize_outroTipo() {
        String errorMessage = "Cannot deserialize Integer from String";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/test");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid data format in request body", response.getBody().getMessage());
    }

    // Testes para cobrir as linhas não cobertas
    @Test
    void handleHttpMessageNotReadable_uuidError_endMenorQueStart() {
        String errorMessage = "Cannot deserialize UUID from String \"\"";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/restaurants");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'ownerUserId'"));
    }

    @Test
    void handleHttpMessageNotReadable_booleanError_endMenorQueStart() {
        String errorMessage = "Cannot deserialize Boolean from String \"\"";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/menu-items");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid value for field 'dineInOnly'"));
    }

    @Test
    void handleHttpMessageNotReadable_comThroughReferenceChain() {
        // Criar uma exceção com causa que contém "through reference chain:"
        RuntimeException cause = new RuntimeException("JSON parse error through reference chain: User[\"userId\"]");
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage, cause);
        
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'userId'"));
    }

    @Test
    void handleHttpMessageNotReadable_comThroughReferenceChain_semFieldName() {
        // Criar uma exceção com causa que contém "through reference chain:" mas sem field name válido
        RuntimeException cause = new RuntimeException("JSON parse error through reference chain: User[]");
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage, cause);
        
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'UUID field'"));
    }

    @Test
    void handleHttpMessageNotReadable_comThroughReferenceChain_semLastBracket() {
        // Criar uma exceção com causa que contém "through reference chain:" mas sem último bracket
        RuntimeException cause = new RuntimeException("JSON parse error through reference chain: User");
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage, cause);
        
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'UUID field'"));
    }

    @Test
    void handleHttpMessageNotReadable_semThroughReferenceChain_currentNull() {
        // Criar uma exceção sem "through reference chain" para testar o loop while com current != null
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage);
        
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'UUID field'"));
    }

    @Test
    void handleHttpMessageNotReadable_comCausaComMensagemNull() {
        // Criar uma exceção com causa que tem getMessage() == null
        RuntimeException causeWithNullMessage = new RuntimeException() {
            @Override
            public String getMessage() {
                return null;
            }
        };
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage, causeWithNullMessage);
        
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'UUID field'"));
    }

    @Test
    void handleHttpMessageNotReadable_comCausaSemThroughReferenceChain() {
        // Criar uma exceção com causa que não contém "through reference chain:"
        RuntimeException causeWithoutChain = new RuntimeException("Some other error message");
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage, causeWithoutChain);
        
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'UUID field'"));
    }

    @Test
    void handleHttpMessageNotReadable_fieldNameJaEncontrado_paraWhileLoop() {
        // Criar uma cadeia de exceções onde o fieldName é encontrado na primeira iteração
        // Isso fará com que fieldName.isEmpty() seja false, parando o loop
        RuntimeException causeWithField = new RuntimeException("JSON parse error through reference chain: User[\"userId\"]");
        RuntimeException outraCausa = new RuntimeException("Outra causa que não deveria ser processada", causeWithField);
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage, outraCausa);
        
        when(request.getRequestURI()).thenReturn("/api/users");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'userId'"));
    }

    @Test
    void handleHttpMessageNotReadable_cadeiaLongaComFieldNameEncontrado() {
        // Criar uma cadeia longa onde o fieldName é encontrado no meio
        // Isso testa o cenário onde current != null mas fieldName já não está vazio
        RuntimeException nivel3 = new RuntimeException("Causa mais profunda");
        RuntimeException nivel2 = new RuntimeException("JSON parse error through reference chain: Restaurant[\"ownerUserId\"]", nivel3);
        RuntimeException nivel1 = new RuntimeException("Causa intermediária que não deveria ser processada", nivel2);
        String errorMessage = "Cannot deserialize UUID from invalid format";
        
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(errorMessage, nivel1);
        
        when(request.getRequestURI()).thenReturn("/api/restaurants");

        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadable(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("Invalid UUID format for field 'ownerUserId'"));
    }

    // Método auxiliar para o teste de validação
    private void dummyMethod(String param) {
        // Método dummy para teste
    }
}