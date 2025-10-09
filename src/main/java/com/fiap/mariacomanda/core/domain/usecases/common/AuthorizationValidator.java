package com.fiap.mariacomanda.core.domain.usecases.common;

import com.fiap.mariacomanda.core.domain.entity.User;

import java.util.UUID;

/**
 * Classe de compatibilidade que delega para {@link RequesterValidator}.
 */
public final class AuthorizationValidator {

    private AuthorizationValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateRequesterUserId(UUID requesterUserId) {
        RequesterValidator.validateRequesterUserId(requesterUserId);
    }

    public static void validateRequester(User requester) {
        RequesterValidator.validateRequester(requester);
    }

    public static void validateRequesterIsOwner(User requester, String operation) {
        RequesterValidator.validateRequesterIsOwner(requester, operation);
    }
}
