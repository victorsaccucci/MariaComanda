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
    public User execute(CreateUserInputDTO inputDTO, UUID requesterUserId) {
        if (inputDTO == null) {
            throw new IllegalArgumentException("CreateUserInputDTO cannot be null");
        }
        User requester = resolveRequester(requesterUserId); // gateway já traz o User completo conforme domain espera. gateway pode conversar com quantos repositórios forem necessários para reconstruir o agregado (User) que o domínio espera
        UserType requesterType = requester.getUserType();
        // verificando se requester é do tipo dono
        if (requesterType == null || !requesterType.isOwner()) {
            throw new IllegalStateException("Only OWNER users can create users");
        }

        // buscando userType que foi definido pelo requester para compor o novo usuário
        UserType userType = resolveUserType(inputDTO.userTypeId());
        // montando o domain, com o userType definido pelo requester
        User user = userMapper.toDomain(inputDTO, userType);
        return userGateway.save(user);
    }

    // busca o usuário que fez a requisição
    private User resolveRequester(UUID requesterUserId) {
        if (requesterUserId == null) {
            throw new IllegalArgumentException("Requester user ID cannot be null");
        }

        return userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
    }

    // busca o UserType que foi definido pelo requester para o novo usuário
    private UserType resolveUserType(UUID userTypeId) {
        if (userTypeId == null) {
            throw new IllegalArgumentException("UserType ID cannot be null");
        }

        return userTypeGateway.findById(userTypeId)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found for id: " + userTypeId));
    }
}
