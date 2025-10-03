package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.usecases.user.DeleteUserUseCase;

import java.util.UUID;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserGateway gateway;

    public DeleteUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(UUID id) {
        gateway.deleteById(id);
    }
}
