package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.usertype.UpdateUserTypeUseCase;

public class UpdateUserTypeUseCaseImpl implements UpdateUserTypeUseCase {

    private final UserTypeGateway gateway;

    public UpdateUserTypeUseCaseImpl(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public UserType execute(UserType userType) {
        if (userType.getId() == null) throw new IllegalArgumentException("id is required");
        return gateway.save(userType);
    }
}
