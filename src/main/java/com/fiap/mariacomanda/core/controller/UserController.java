package com.fiap.mariacomanda.core.controller;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.*;
import com.fiap.mariacomanda.core.mapper.UserMapper;
import com.fiap.mariacomanda.core.usecases.user.*;

import java.util.List;

public class UserController {
    private final CreateUserUseCase create;
    private final GetUserUseCase get;
    private final ListUserUseCase list;
    private final UpdateUserUseCase update;
    private final DeleteUserUseCase delete;
    private final UserMapper userMapper;

    public UserController(CreateUserUseCase create, GetUserUseCase get, ListUserUseCase list, UpdateUserUseCase update, DeleteUserUseCase delete, UserMapper userMapper) {
        this.create = create;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
        this.userMapper = userMapper;
    }

    public CreateUserOutputDTO create(CreateUserInputDTO inputDTO) {
        User user = userMapper.mapCreateInputToDomain(inputDTO);
        User created = create.execute(user);
        return userMapper.mapCreateDomainToOutput(created);
    }

    public void delete(DeleteUserInputDTO inputDTO){
        delete.execute(inputDTO.getId());
    }

    public GetUserOutputDTO get(GetUserInputDTO inputDTO) {
        User user = get.execute(inputDTO.getId())
                .orElseThrow(() -> new RuntimeException("User item not found"));
        return userMapper.mapGetDomainToOutput(user);
    }

    public List<GetUserOutputDTO> list(ListUserInputDTO inputDTO) {
        List<User> users = list.execute(inputDTO.getPage(), inputDTO.getSize());
        return userMapper.mapGetMenuItemOutputDtoToList(users);
    }

    public GetUserOutputDTO update(UpdateUsersInputDTO inputDTO) {
        User user = userMapper.mapUpdateInputToDomain(inputDTO);
        User updated = update.execute(user);
        return userMapper.mapGetDomainToOutput(updated);
    }
}
