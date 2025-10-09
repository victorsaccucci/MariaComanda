package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import java.util.UUID;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.common.AuthorizationValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.UserTypeValidator;
import com.fiap.mariacomanda.core.domain.usecases.user.CreateUserUseCase;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.mapper.UserMapper;

public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;
    private final UserMapper userMapper;

    public CreateUserUseCaseImpl(UserGateway userGateway, UserTypeGateway userTypeGateway,
                                UserMapper userMapper) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
        this.userMapper = userMapper;
    }

    @Override
    public User execute(CreateUserInputDTO inputDTO, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(inputDTO, CreateUserInputDTO.class.getName());

        AuthorizationValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        AuthorizationValidator.validateRequesterIsOwner(requester, "create users");

        // buscando userType que foi definido pelo requester para compor o novo usuário
        NullObjectValidator.validateNotNull(inputDTO.userTypeId(), "userTypeId");
        UserType userType = userTypeGateway.findById(inputDTO.userTypeId())
                .orElseThrow(() -> new IllegalArgumentException("UserType not found for id: " + inputDTO.userTypeId()));
        UserTypeValidator.validateUserTypeForUserCreation(userType);

        // montando o domain, com o userType definido pelo requester
        User user = userMapper.toDomain(inputDTO, userType);
        return userGateway.save(user);
    }
}
