package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.usertype.CreateUserTypeUseCase;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class CreateUserTypeUseCaseImpl implements CreateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;
    private final UserGateway userGateway;
    private final UserTypeMapper userTypeMapper;

    public CreateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway, UserGateway userGateway,
                                    UserTypeMapper userTypeMapper) {
        this.userTypeGateway = userTypeGateway;
        this.userGateway = userGateway;
        this.userTypeMapper = userTypeMapper;
    }

    @Override
    public UserType execute(CreateUserTypeInputDTO inputDTO, UUID requesterUserId) {

        NullObjectValidator.validateNotNull(inputDTO, CreateUserTypeInputDTO.class.getName());

        RequesterValidator.validateRequesterUserId(requesterUserId);

        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));

        RequesterValidator.validateRequesterIsOwner(requester, "create user types");

        NullObjectValidator.validateNotNull(inputDTO.name(), "User type name");
        if (inputDTO.name() != null && inputDTO.name().trim().isEmpty()) {
            throw new IllegalArgumentException("User type name cannot be empty");
        }
        NullObjectValidator.validateNotNull(inputDTO.subType(), "User type subType");
        if (inputDTO.subType() != null && !inputDTO.subType().matches("(?i)customer|owner")) {
            throw new IllegalArgumentException("User type subType must be CUSTOMER or OWNER");
        }

        UserType userType = userTypeMapper.toDomain(inputDTO);

        return userTypeGateway.save(userType);
    }
}
