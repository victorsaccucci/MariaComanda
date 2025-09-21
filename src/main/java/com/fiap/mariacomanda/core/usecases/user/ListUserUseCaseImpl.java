package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.gateway.UserGateway;

import java.util.List;

public class ListUserUseCaseImpl implements ListUserUseCase {

    private final UserGateway repository;

    public ListUserUseCaseImpl(UserGateway repository) {
        this.repository = repository;
    }

    @Override
    public List<User> execute(int page, int size) {
        return repository.findAll(page, size);
    }
}
