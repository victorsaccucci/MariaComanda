package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.usecases.user.CreateUserUseCase;

public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserGateway gateway;

    public CreateUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public User execute(User user) {
        return gateway.save(user);
    }
}
