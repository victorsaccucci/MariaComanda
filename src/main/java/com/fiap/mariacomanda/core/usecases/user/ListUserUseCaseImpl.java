package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.gateway.UserGateway;

import java.util.List;

public class ListUserUseCaseImpl implements ListUserUseCase{

    private final UserGateway gateway;

    public ListUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<User> execute(int page, int size) {
        return gateway.findAll(page, size);
    }
}
