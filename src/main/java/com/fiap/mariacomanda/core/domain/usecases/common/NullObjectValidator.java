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
        if (object == null) {
            throw new IllegalArgumentException(objectName + " cannot be null");
        }
    }

    public static void validateNotNullOrEmpty(String value, String fieldName) {
        validateNotNull(value, fieldName);
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }
}