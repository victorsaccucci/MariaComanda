package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.domain.entity.UserType;

/**
 * Helper para validações de tipo de usuário.
 * Métodos estáticos, sem dependências externas.
 */
public final class UserTypeValidator {

    private UserTypeValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateUserTypeForUserCreation(UserType userType) {
        if (userType == null) {
            throw new IllegalArgumentException("UserType cannot be null");
        }
        // Regras específicas podem ser adicionadas aqui
        // Por exemplo: validar se o tipo está ativo, se permite criação, etc.
    }
}