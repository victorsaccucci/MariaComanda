package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.usecases.user.DeleteUserUseCase;

import java.util.UUID;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserGateway repository;

    public DeleteUserUseCaseImpl(UserGateway repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID id) {
        repository.deleteById(id);
    }
}
