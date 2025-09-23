package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.usecases.user.CreateUserUseCase;

public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserGateway repository;

    public CreateUserUseCaseImpl(UserGateway repository) {
        this.repository = repository;
    }

    @Override
    public User execute(User user) {
        return repository.save(user);
    }
}
