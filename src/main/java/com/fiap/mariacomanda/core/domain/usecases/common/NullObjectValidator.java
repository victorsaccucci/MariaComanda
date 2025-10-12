package com.fiap.mariacomanda.core.domain.usecases.common;

/**
 * Helper para validações de objetos nulos.
 * Métodos estáticos, sem dependências externas.
 */
public final class NullObjectValidator {

    private NullObjectValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateNotNull(Object object, String objectName) {
        // Validação objeto nao pode ser null
        if (object == null) {
            throw new IllegalArgumentException(objectName + " cannot be null");
        }
    }
}
