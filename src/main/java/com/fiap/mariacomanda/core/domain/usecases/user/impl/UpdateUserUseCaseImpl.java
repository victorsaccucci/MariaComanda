package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.user.UpdateUserUseCase;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserGateway gateway;

    public UpdateUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public User execute(User user) {
        if (user.getId() == null) throw new IllegalArgumentException("id is required");
        return gateway.save(user);
    }
}
