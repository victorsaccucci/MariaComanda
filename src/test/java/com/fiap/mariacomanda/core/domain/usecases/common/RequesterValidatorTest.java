package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RequesterValidatorTest {

    @Test
    @DisplayName("validateRequesterUserId - lança exceção para id nulo")
    void validateRequesterUserId_deveLancarExcecaoParaNull() {
        ValidationException ex = assertThrows(ValidationException.class, () -> 
            RequesterValidator.validateRequesterUserId(null)
        );
        assertEquals("Requester user ID cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("validateRequesterUserId - não lança exceção para id válido")
    void validateRequesterUserId_deveAceitarIdValido() {
        assertDoesNotThrow(() ->
            RequesterValidator.validateRequesterUserId(UUID.randomUUID())
        );
    }

    @Test
    @DisplayName("validateRequester - lança exceção para user nulo")
    void validateRequester_deveLancarExcecaoParaNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            RequesterValidator.validateRequester(null)
        );
        assertEquals("Requester user cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("validateRequester - não lança exceção para user preenchido")
    void validateRequester_naoLancaParaUserPreenchido() {
        UserType ownerType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User user = new User(UUID.randomUUID(), "Nome", "mail@dominio.com", "senha", ownerType);
        assertDoesNotThrow(() ->
            RequesterValidator.validateRequester(user)
        );
    }

    @Test
    @DisplayName("validateRequesterIsOwner - lança exceção se user=null")
    void validateRequesterIsOwner_deveLancarParaUserNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            RequesterValidator.validateRequesterIsOwner(null, "criar recurso")
        );
        assertEquals("Requester user cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("validateRequesterIsOwner - lança exceção se tipo for CUSTOMER")
    void validateRequesterIsOwner_deveLancarParaTipoCustomer() {
        UserType customerType = new UserType(UUID.randomUUID(), "CUSTOMER", UserType.CUSTOMER);
        User user = new User(UUID.randomUUID(), "Cliente", "cliente@ex.com", "senha", customerType);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
            RequesterValidator.validateRequesterIsOwner(user, "deletar restaurante")
        );
        assertEquals("Only OWNER users can deletar restaurante", ex.getMessage());
    }

    @Test
    @DisplayName("validateRequesterIsOwner - permite acesso para OWNER")
    void validateRequesterIsOwner_permiteParaOwner() {
        UserType ownerType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User user = new User(UUID.randomUUID(), "Dono", "dono@ex.com", "senha", ownerType);
        assertDoesNotThrow(() ->
            RequesterValidator.validateRequesterIsOwner(user, "gerenciar sistema")
        );
    }

    @Test
    void constructorShouldThrowExceptionWhenInvoked() throws Exception {
        Constructor<RequesterValidator> constructor = RequesterValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertEquals(null, exception.getMessage());
    }

}