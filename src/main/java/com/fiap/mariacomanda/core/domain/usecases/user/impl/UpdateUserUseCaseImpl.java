package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.user.UpdateUserUseCase;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.mapper.UserMapper;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserGateway userGateway;
    private final UserTypeGateway userTypeGateway;
    private final UserMapper userMapper;

    public UpdateUserUseCaseImpl(UserGateway userGateway, UserTypeGateway userTypeGateway, UserMapper userMapper) {
        this.userGateway = userGateway;
        this.userTypeGateway = userTypeGateway;
        this.userMapper = userMapper;
    }

    @Override
    public User execute(UpdateUserInputDTO inputDTO) {
        if (inputDTO == null) {
            throw new IllegalArgumentException("UpdateUserInputDTO cannot be null");
        }
        if (inputDTO.id() == null) {
            throw new IllegalArgumentException("User id is required");
        }

        User existing = userGateway.findById(inputDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // se foi selecionado um novo userType, aqui atualiza para o novo selecionado, se não mantem o existente
        UserType userType = inputDTO.userTypeId() != null
                ? resolveUserType(inputDTO.userTypeId())
                : existing.getUserType();
        // usuario com dados atualizados, de acordo com que foi passado pelo requester
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
        if (userTypeId == null) {
            throw new IllegalArgumentException("UserType ID cannot be null");
        }

        return userTypeGateway.findById(userTypeId)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found for id: " + userTypeId));
    }

    private String updateValue(String newValue, String current) {
        return newValue != null ? newValue : current;
    }
}
