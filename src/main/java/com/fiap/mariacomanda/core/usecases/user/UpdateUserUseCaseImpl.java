package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.gateway.UserGateway;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase{

    private final UserGateway gateway;

    public UpdateUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public User execute(User user) {
        if (user.id() == null) throw new IllegalArgumentException("id is required");
        return gateway.save(user);
    }
}
