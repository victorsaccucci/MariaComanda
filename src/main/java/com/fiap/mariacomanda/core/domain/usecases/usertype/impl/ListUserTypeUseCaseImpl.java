package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.usertype.ListUserTypeUseCase;

import java.util.List;

public class ListUserTypeUseCaseImpl implements ListUserTypeUseCase {

    private final UserTypeGateway gateway;

    public ListUserTypeUseCaseImpl(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<UserType> execute(int page, int size) {
        return gateway.findAll(page, size);
    }
}
