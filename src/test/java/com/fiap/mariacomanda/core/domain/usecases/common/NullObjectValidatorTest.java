package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class NullObjectValidatorTest {

    @Test
    @DisplayName("Não lança exceção quando objeto não é nulo")
    void validateNotNull_naoLancaExcecaoParaObjetoNaoNulo() {
        assertDoesNotThrow(() -> 
            NullObjectValidator.validateNotNull("objeto não nulo", "nomeObjeto")
        );
    }

    @Test
    @DisplayName("Lança ValidationException quando objeto for nulo")
    void validateNotNull_lancaValidationExceptionParaObjetoNulo() {
        ValidationException ex = assertThrows(ValidationException.class, () -> 
            NullObjectValidator.validateNotNull(null, "TesteDeObjeto")
        );
        assertEquals("TesteDeObjeto cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Construtor privado lança IllegalStateException")
    void construtorPrivado_naoInstanciavel() throws Exception {
        var constructor = NullObjectValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException ex = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertEquals(null, ex.getMessage());
    }
}