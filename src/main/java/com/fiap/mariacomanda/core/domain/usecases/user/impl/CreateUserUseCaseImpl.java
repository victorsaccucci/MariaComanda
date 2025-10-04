package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import java.util.UUID;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.user.CreateUserUseCase;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.mapper.UserMapper;

public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;
    private final UserMapper userMapper;

    public CreateUserUseCaseImpl(UserGateway userGateway, UserTypeGateway userTypeGateway, UserMapper userMapper) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
        this.userMapper = userMapper;
    }

    @Override
    public User execute(CreateUserInputDTO inputDTO) {
        if (inputDTO == null) {
            throw new IllegalArgumentException("CreateUserInputDTO cannot be null");
        }

        UserType userType = resolveUserType(inputDTO.userTypeId());
        User user = userMapper.toDomain(inputDTO, userType);
        return userGateway.save(user);
    }

    private UserType resolveUserType(UUID userTypeId) {
        if (userTypeId == null) {
            throw new IllegalArgumentException("UserType ID cannot be null");
        }

        return userTypeGateway.findById(userTypeId)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found for id: " + userTypeId));
    }
}
