package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.usertype.CreateUserTypeUseCase;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class CreateUserTypeUseCaseImpl implements CreateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;
    private final UserGateway userGateway;
    private final UserTypeMapper userTypeMapper;
    private final NullObjectValidator nullObjectValidator;

    public CreateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway, UserGateway userGateway,
                                    UserTypeMapper userTypeMapper, NullObjectValidator nullObjectValidator) {
        this.userTypeGateway = userTypeGateway;
        this.userGateway = userGateway;
        this.userTypeMapper = userTypeMapper;
        this.nullObjectValidator = nullObjectValidator;
    }

    @Override
    public UserType execute(CreateUserTypeInputDTO inputDTO, UUID requesterUserId) {
        nullObjectValidator.validateNotNull(inputDTO, CreateUserTypeInputDTO.class.getName());

        if (requesterUserId == null) {
            throw new IllegalArgumentException("requesterUserId is required");
        }

        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));

        UserType requesterType = requester.getUserType();
        if (requesterType == null || !requesterType.isOwner()) {
            log.warn("User {} attempted to create a user type without OWNER privileges. Current type: {}",
                    requesterUserId, requesterType != null ? requesterType.getSubType() : "unknown");
            throw new IllegalStateException("Only OWNER users can create user types");
        }

        UserType userType = userTypeMapper.toDomain(inputDTO);

        return userTypeGateway.save(userType);
    }
}
