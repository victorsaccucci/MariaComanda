package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.usecases.usertype.CreateUserTypeUserCase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateUserTypeUseCaseImpl implements CreateUserTypeUserCase {

    private final UserTypeGateway gateway;

    public CreateUserTypeUseCaseImpl(UserTypeGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public UserType execute(UserType userType) {
        return gateway.save(userType);
    }
}
