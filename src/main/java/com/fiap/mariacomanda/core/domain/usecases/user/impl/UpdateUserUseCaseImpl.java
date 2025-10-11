package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.UserTypeValidator;
import com.fiap.mariacomanda.core.domain.usecases.user.UpdateUserUseCase;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;

import java.util.UUID;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;

    public UpdateUserUseCaseImpl(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public User execute(UpdateUserInputDTO inputDTO, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(inputDTO, UpdateUserInputDTO.class.getName());
        NullObjectValidator.validateNotNull(inputDTO.id(), "userId");

        RequesterValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        RequesterValidator.validateRequesterIsOwner(requester, "update users");

        User existing = userGateway.findById(inputDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        NullObjectValidator.validateNotNull(existing, "existingUser");

        UserType userType = inputDTO.userTypeId() != null
                ? resolveUserType(inputDTO.userTypeId())
                : existing.getUserType();
        if (inputDTO.userTypeId() != null) {
            UserTypeValidator.validateUserType(userType);
        }

        User merged = new User(
            existing.getId(),
            updateValue(inputDTO.name(), existing.getName()),
            updateValue(inputDTO.email(), existing.getEmail()),
            updateValue(inputDTO.password(), existing.getPasswordHash()),
            userType
        );

        return userGateway.save(merged);
    }

    // busca novo userTypeId passado pelo requester
    private UserType resolveUserType(java.util.UUID userTypeId) {
        NullObjectValidator.validateNotNull(userTypeId, "userTypeId");

        return userTypeGateway.findById(userTypeId)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found for id: " + userTypeId));
    }

    private String updateValue(String newValue, String current) {
        return newValue != null ? newValue : current;
    }
}
