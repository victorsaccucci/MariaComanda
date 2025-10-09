package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.common.AuthorizationValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.usertype.DeleteUserTypeUseCase;

import java.util.UUID;

public class DeleteUserTypeUseCaseImpl implements DeleteUserTypeUseCase {

    private final UserTypeGateway gateway;
    private final UserGateway userGateway;

    public DeleteUserTypeUseCaseImpl(UserTypeGateway gateway, UserGateway userGateway) {
        this.gateway = gateway;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UUID id, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(id, "userTypeId");

        AuthorizationValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        AuthorizationValidator.validateRequesterIsOwner(requester, "delete user types");

        UserType existing = gateway.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found for id: " + id));
        NullObjectValidator.validateNotNull(existing, "existingUserType");

        gateway.deleteById(id);
    }
}
