package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeValidatorTest {

    @Test
    @DisplayName("Lança ValidationException quando userType é null")
    void validateUserType_deveLancarParaNull() {
        ValidationException ex = assertThrows(ValidationException.class, () ->
            UserTypeValidator.validateUserType(null)
        );
        assertEquals("UserType cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Não lança exceção para userType válido")
    void validateUserType_naoLancaParaUserTypeValido() {
        UserType userType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        assertDoesNotThrow(() ->
            UserTypeValidator.validateUserType(userType)
        );
    }

    @Test
    void constructorShouldThrowExceptionWhenInvoked() throws Exception {
        Constructor<UserTypeValidator> constructor = UserTypeValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertEquals(null, exception.getMessage());
    }
}