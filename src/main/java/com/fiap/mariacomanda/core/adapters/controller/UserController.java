package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.user.*;
import com.fiap.mariacomanda.core.dto.user.input.*;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserMapper;

import java.util.List;
import java.util.UUID;

public class UserController {
    private final CreateUserUseCase createUseCase;
    private final GetUserUseCase getUseCase;
    private final ListUserUseCase listUseCase;
    private final UpdateUserUseCase updateUseCase;
    private final DeleteUserUseCase deleteUseCase;
    private final UserMapper userMapper;

    public UserController(CreateUserUseCase createUseCase, GetUserUseCase getUseCase, ListUserUseCase listUseCase,
                          UpdateUserUseCase updateUseCase, DeleteUserUseCase deleteUseCase, UserMapper userMapper) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.userMapper = userMapper;
    }

    public CreateUserOutputDTO create(CreateUserInputDTO inputDTO, UUID requesterUserId) {
        User created = createUseCase.execute(inputDTO, requesterUserId);
        return userMapper.toCreateOutput(created);
    }

    public GetUserOutputDTO get(GetUserInputDTO inputDTO) {
        User user = getUseCase.execute(inputDTO.id()).orElseThrow();
        return userMapper.toGetOutput(user);
    }

    public List<GetUserOutputDTO> list(ListUserInputDTO inputDTO) {
        List<User> users = listUseCase.execute(inputDTO.page(), inputDTO.size());
        return userMapper.toGetOutputList(users);
    }

    public GetUserOutputDTO update(UpdateUserInputDTO inputDTO, UUID requesterUserId) {
        User updated = updateUseCase.execute(inputDTO, requesterUserId);
        return userMapper.toGetOutput(updated);
    }

    public void delete(DeleteUserInputDTO inputDTO) {
        UUID id = inputDTO.id();
        deleteUseCase.execute(id);
    }

}
