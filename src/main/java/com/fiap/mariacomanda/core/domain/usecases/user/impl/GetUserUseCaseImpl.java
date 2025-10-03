package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.user.GetUserUseCase;

import java.util.Optional;
import java.util.UUID;

public class GetUserUseCaseImpl implements GetUserUseCase {

    private final UserGateway gateway;

    public GetUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Optional<User> execute(UUID id) {
        return gateway.findById(id);
    }
}
