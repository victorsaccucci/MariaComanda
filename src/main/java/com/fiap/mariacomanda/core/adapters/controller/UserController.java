package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.user.*;
import com.fiap.mariacomanda.core.dto.user.input.*;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.mapper.impl.UserMapperImpl;

import java.util.List;
import java.util.UUID;

public class UserController {
    private final CreateUserUseCase createUseCase;
    private final GetUserUseCase getUseCase;
    private final ListUserUseCase listUseCase;
    private final UpdateUserUseCase updateUseCase;
    private final DeleteUserUseCase deleteUseCase;
    private final UserMapperImpl userMapper;

    public UserController(CreateUserUseCase createUseCase, GetUserUseCase getUseCase, ListUserUseCase listUseCase,
                         UpdateUserUseCase updateUseCase, DeleteUserUseCase deleteUseCase, UserMapperImpl userMapper) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.userMapper = userMapper;
    }

    public CreateUserOutputDTO create(CreateUserInputDTO inputDTO) {
        User user = userMapper.mapCreateInputToDomain(inputDTO);
        User created = createUseCase.execute(user);
        return userMapper.mapCreateDomainToOutput(created);
    }

    public void delete(DeleteUserInputDTO inputDTO) {
        UUID id = inputDTO.id();
        deleteUseCase.execute(id);
    }

    public GetUserOutputDTO get(GetUserInputDTO inputDTO) {
        User user = getUseCase.execute(inputDTO.id()).orElseThrow();
        return userMapper.mapGetDomainToOutput(user);
    }

    public List<GetUserOutputDTO> list(ListUserInputDTO inputDTO) {
        List<User> users = listUseCase.execute(inputDTO.page(), inputDTO.size());
        return userMapper.mapGetUserOutputDtoToList(users);
    }

    public GetUserOutputDTO update(UpdateUsersInputDTO inputDTO) {
        User user = userMapper.mapUpdateInputToDomain(inputDTO);
        User updated = updateUseCase.execute(user);
        return userMapper.mapGetDomainToOutput(updated);
    }
}
