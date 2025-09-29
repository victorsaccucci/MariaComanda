package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.usertype.UpdateUserTypeUseCase;

public class UpdateUserTypeUseCaseImpl implements UpdateUserTypeUseCase {

    private final UserTypeGateway repository;

    public UpdateUserTypeUseCaseImpl(UserTypeGateway repository) {
        this.repository = repository;
    }

    @Override
    public UserType execute(UserType user) {
        if (user.getId() == null) throw new IllegalArgumentException("id is required");
        return repository.save(user);
    }
}
