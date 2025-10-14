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
    void handleDataIntegrityViolation_deleteReferenciadoPorOutraTabela() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Alguma mensagem sem MENU_ITEM");
        when(request.getMethod()).thenReturn("DELETE");
        when(request.getRequestURI()).thenReturn("/api/restaurants/1");

        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Cannot delete restaurant because it has menu items. Delete menu items first", response.getBody().getMessage());
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
    void handleDataIntegrityViolation_DELETE_MENU_ITEM() {
        DataIntegrityViolationException ex =
                new DataIntegrityViolationException("Erro violando MENU_ITEM (chave estrangeira)",
                        new RuntimeException("MENU_ITEM"));

        when(request.getMethod()).thenReturn("DELETE");
        when(request.getRequestURI()).thenReturn("/exemplo/restaurants/123");

        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(
                "Cannot delete restaurant because it has menu items. Delete menu items first",
                response.getBody().getMessage()
        );
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
    void handleTypeMismatch_deveRetornarBadRequestComMensagemEsperada() {
        Object valorInvalido = "abc123";
        String parametro = "userId";
        Class<?> tipoEsperado = Long.class;
        String uri = "/api/users/params";

        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                valorInvalido, tipoEsperado, parametro, null, new IllegalArgumentException("erro de tipo")
        );
        when(request.getRequestURI()).thenReturn(uri);

        ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse body = response.getBody();
        assertTrue(body.getMessage().contains("Invalid value 'abc123' for parameter 'userId'. Expected type: Long"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), body.getError());
        assertEquals(uri, body.getPath());
    }

    @Test
    void handleEntityNotFound_deveRetornar404ENomeFormatado() {
        String entity = "MenuItem";
        String id = "xpto-777";
        EntityNotFoundException ex = new EntityNotFoundException(entity, id);
        String path = "/api/menu-items/xpto-777";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleEntityNotFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse err = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), err.getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), err.getError());
        assertEquals("MenuItem not found with identifier: xpto-777", err.getMessage());
        assertEquals(path, err.getPath());
        assertNotNull(err.getTimestamp());
    }

    @Test
    void handleValidation_deveRetornar400ComMensagem() {
        String erroMsg = "Campo nome é obrigatório";
        ValidationException ex = new ValidationException(erroMsg);
        String path = "/api/valida";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleValidation(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse error = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), error.getError());
        assertEquals(erroMsg, error.getMessage());
        assertEquals(path, error.getPath());
        assertNotNull(error.getTimestamp());
    }

    @Test
    void handleUnauthorized_deveRetornar403ComMensagem() {
        UnauthorizedException ex = new UnauthorizedException("Usuário não permitido");
        String path = "/api/protected";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ErrorResponse> response = handler.handleUnauthorized(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        ErrorResponse erro = response.getBody();
        assertEquals(HttpStatus.FORBIDDEN.value(), erro.getStatus());
        assertEquals(HttpStatus.FORBIDDEN.getReasonPhrase(), erro.getError());
        assertEquals("Usuário não permitido", erro.getMessage());
        assertEquals(path, erro.getPath());
        assertNotNull(erro.getTimestamp());
    }

    @Test
    void handleDataIntegrityViolation_deleteSemMensagemMenuItem_deveCairNoElse() {
        DataIntegrityViolationException ex =
            new DataIntegrityViolationException("Existe um pedido vinculado a este restaurante.");
        when(request.getMethod()).thenReturn("DELETE");
        when(request.getRequestURI()).thenReturn("/api/restaurants/2");

        ResponseEntity<ErrorResponse> response = handler.handleDataIntegrityViolation(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Cannot delete resource because it is referenced by other data", response.getBody().getMessage());
        assertEquals("/api/restaurants/2", response.getBody().getPath());
    }

    @Test
    void handleMethodArgumentNotValid_deveRetornarTodosOsFieldErrors() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError erro1 = new FieldError("obj", "nome", "não deve ser vazio");
        FieldError erro2 = new FieldError("obj", "preco", "deve ser maior que zero");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(erro1, erro2));

        Method method = this.getClass().getDeclaredMethod("dummyMethod", String.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        String path = "/api/produtos";
        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<ValidationErrorResponse> response = handler.handleMethodArgumentNotValid(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ValidationErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Validation failed", body.getMessage());
        assertEquals(path, body.getPath());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), body.getError());
        assertNotNull(body.getFieldErrors());
        assertEquals(2, body.getFieldErrors().size());
        assertEquals("nome", body.getFieldErrors().get(0).getField());
        assertEquals("não deve ser vazio", body.getFieldErrors().get(0).getMessage());
        assertEquals("preco", body.getFieldErrors().get(1).getField());
        assertEquals("deve ser maior que zero", body.getFieldErrors().get(1).getMessage());
        assertNotNull(body.getTimestamp());
    }

    @SuppressWarnings("unused")
    private void dummyMethod(String test) {}



}