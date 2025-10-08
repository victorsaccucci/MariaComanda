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
    private final NullObjectValidator nullObjectValidator;
    private final AuthorizationValidator authorizationValidator;
    private final UserTypeValidator userTypeValidator;

    public CreateUserUseCaseImpl(UserGateway userGateway, UserTypeGateway userTypeGateway,
                                UserMapper userMapper, NullObjectValidator nullObjectValidator,
                                AuthorizationValidator authorizationValidator, UserTypeValidator userTypeValidator) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
        this.userMapper = userMapper;
        this.nullObjectValidator = nullObjectValidator;
        this.authorizationValidator = authorizationValidator;
        this.userTypeValidator = userTypeValidator;
    }

    @Override
    public User execute(CreateUserInputDTO inputDTO, UUID requesterUserId) {
        nullObjectValidator.validateNotNull(inputDTO, CreateUserInputDTO.class.getName());

        authorizationValidator.validateRequesterAndAuthorizeOwner(requesterUserId, "create users");

        // buscando userType que foi definido pelo requester para compor o novo usuário
        UserType userType = userTypeValidator.validateAndFindUserType(inputDTO.userTypeId());
        userTypeValidator.validateUserTypeForUserCreation(userType);

        // montando o domain, com o userType definido pelo requester
        User user = userMapper.toDomain(inputDTO, userType);
        return userGateway.save(user);
    }
}
