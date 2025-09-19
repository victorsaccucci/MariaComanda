package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.gateway.UserGateway;
import java.util.UUID;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase{

    private final UserGateway repository;

    public DeleteUserUseCaseImpl(UserGateway repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID id) {
        repository.deleteById(id);
    }
}
