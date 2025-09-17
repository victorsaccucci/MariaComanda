package com.fiap.mariacomanda.core.usecases.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.gateway.UserGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


public class CreateUserUseCaseImpl implements CreateUserUseCase{
    private final UserGateway gateway;

    public CreateUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public User execute(User user) {
        return gateway.save(user);
    }
}
