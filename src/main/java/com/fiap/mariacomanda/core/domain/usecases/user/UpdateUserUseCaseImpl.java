package com.fiap.mariacomanda.core.domain.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.gateway.UserGateway;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserGateway repository;

    public UpdateUserUseCaseImpl(UserGateway repository) {
        this.repository = repository;
    }

    @Override
    public User execute(User user) {
        if (user.id() == null) throw new IllegalArgumentException("id is required");
        return repository.save(user);
    }
}
