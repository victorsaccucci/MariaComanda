package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.common.AuthorizationValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.UserTypeValidator;
import com.fiap.mariacomanda.core.domain.usecases.usertype.UpdateUserTypeUseCase;

import java.util.UUID;

public class UpdateUserTypeUseCaseImpl implements UpdateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;
    private final UserGateway userGateway;

    public UpdateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway, UserGateway userGateway) {
        this.userTypeGateway = userTypeGateway;
        this.userGateway = userGateway;
    }

    @Override
    public UserType execute(UserType userType, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(userType, UserType.class.getName());
        NullObjectValidator.validateNotNull(userType.getId(), "userTypeId");

        AuthorizationValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        AuthorizationValidator.validateRequesterIsOwner(requester, "update user types");

        userTypeGateway.findById(userType.getId())
                .orElseThrow(() -> new IllegalArgumentException("UserType not found for id: " + userType.getId()));

        UserTypeValidator.validateUserType(userType);

        return userTypeGateway.save(userType);
    }
}
