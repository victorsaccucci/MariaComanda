package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.gateway.UserGateway;

import java.util.UUID;

@
public class DeleteUserUseCaseImpl implements DeleteUserUseCase{

    private final UserGateway gateway;

    public DeleteUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(UUID id) {
         gateway.deleteById(id);
    }
}
