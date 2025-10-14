package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;

import java.util.UUID;

/**
 * Helper para validações de autorização.
 * Métodos estáticos, sem dependências externas.
 */
public final class RequesterValidator {

    private RequesterValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateRequesterUserId(UUID requesterUserId) {
        if (requesterUserId == null) {
            throw new ValidationException("Requester user ID cannot be null");
        }
    }

    public static void validateRequester(User requester) {
        if (requester == null) {
            throw new IllegalArgumentException("Requester user cannot be null");
        }
    }

    public static void validateRequesterIsOwner(User requester, String operation) {
        validateRequester(requester);

        UserType requesterType = requester.getUserType();
        if (requesterType == null || !requesterType.isOwner()) {
            throw new IllegalStateException("Only OWNER users can " + operation);
        }
    }
}
