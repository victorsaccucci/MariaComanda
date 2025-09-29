package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.usertype.GetUserTypeUseCase;

import java.util.Optional;
import java.util.UUID;

public class GetUserTypeUseCaseImpl implements GetUserTypeUseCase {

    private final UserTypeGateway repository;

    public GetUserTypeUseCaseImpl(UserTypeGateway repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserType> execute(UUID id) {
        return repository.findById(id);
    }
}
