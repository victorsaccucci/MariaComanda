package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.usertype.ListUserTypeUseCase;

import java.util.List;

public class ListUserTypeUseCaseImpl implements ListUserTypeUseCase {

    private final UserTypeGateway repository;

    public ListUserTypeUseCaseImpl(UserTypeGateway repository) {
        this.repository = repository;
    }

    @Override
    public List<UserType> execute(int page, int size) {
        return repository.findAll(page, size);
    }
}
