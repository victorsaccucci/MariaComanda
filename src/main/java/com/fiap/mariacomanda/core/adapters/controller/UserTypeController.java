package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.usertype.*;
import com.fiap.mariacomanda.core.dto.usertype.input.*;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;

import java.util.List;
import java.util.UUID;

public class UserTypeController {
    private final CreateUserTypeUseCase createUseCase;
    private final GetUserTypeUseCase getUseCase;
    private final ListUserTypeUseCase listUseCase;
    private final UpdateUserTypeUseCase updateUseCase;
    private final DeleteUserTypeUseCase deleteUseCase;
    private final UserTypeMapper userMapper;

    public UserTypeController(CreateUserTypeUseCase createUseCase, GetUserTypeUseCase getUseCase, ListUserTypeUseCase listUseCase, UpdateUserTypeUseCase updateUseCase, DeleteUserTypeUseCase deleteUseCase, UserTypeMapper userMapper) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.userMapper = userMapper;
    }

    public CreateUserTypeOutputDTO create(CreateUserTypeInputDTO inputDTO) {
        UserType user = userMapper.mapCreate(inputDTO);
        UserType created = createUseCase.execute(user);
        return userMapper.mapCreate(created);
    }

    public GetUserTypeOutputDTO get(GetUserTypeInputDTO inputDTO) {
        UserType user = getUseCase.execute(inputDTO.id()).orElseThrow();
        return userMapper.mapToGetOutputDTO(user);
    }

    public List<GetUserTypeOutputDTO> list(ListUserTypeInputDTO inputDTO) {
        List<UserType> users = listUseCase.execute(inputDTO.page(), inputDTO.size());
        return userMapper.mapToGetOutputDTOList(users);
    }

    public GetUserTypeOutputDTO update(UpdateUserTypeInputDTO inputDTO) {
        UserType user = userMapper.mapUpdate(inputDTO);
        UserType updated = updateUseCase.execute(user);
        return userMapper.mapToGetOutputDTO(updated);
    }

    public void delete(DeleteUserTypeInputDTO inputDTO) {
        UUID id = inputDTO.id();
        deleteUseCase.execute(id);
    }
}
