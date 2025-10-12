package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.user.DeleteUserUseCase;

import java.util.UUID;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserGateway gateway;

    public DeleteUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(UUID id, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(id, "userId");

        RequesterValidator.validateRequesterUserId(requesterUserId);
        User requester = gateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        RequesterValidator.validateRequesterIsOwner(requester, "delete users");

        gateway.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + id));

        gateway.deleteById(id);
    }
}
