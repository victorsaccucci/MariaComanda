package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.usecases.usertype.DeleteUserTypeUseCase;

import java.util.UUID;

public class DeleteUserTypeUseCaseImpl implements DeleteUserTypeUseCase {

    private final UserTypeGateway gateway;

    public DeleteUserTypeUseCaseImpl(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(UUID id) {
        gateway.deleteById(id);
    }
}
