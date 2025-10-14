package com.fiap.mariacomanda.core.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnauthorizedExceptionTest {

    @Test
    void shouldStoreMessageCorrectly() {
        String expectedMessage = "User not authorized";
        UnauthorizedException exception = new UnauthorizedException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldBeInstanceOfBusinessException() {
        UnauthorizedException exception = new UnauthorizedException("test");

        assertTrue(exception instanceof BusinessException);
    }

}