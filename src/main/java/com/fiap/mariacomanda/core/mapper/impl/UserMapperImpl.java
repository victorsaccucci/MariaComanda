package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapperImpl implements UserMapper {

    @Override
    public User toDomain(CreateUserInputDTO dto, UserType userType) {
        return buildUser(null, dto.name(), dto.email(), dto.password(), userType);
    }

    @Override
    public User toDomain(UpdateUserInputDTO dto, UserType userType) {
        return buildUser(dto.id(), dto.name(), dto.email(), dto.password(), userType);
    }

    @Override
    public CreateUserOutputDTO toCreateOutput(User user) {
        return new CreateUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                toUserTypeOutput(user.getUserType())
        );
    }

    @Override
    public GetUserOutputDTO toGetOutput(User user) {
        return new GetUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                toUserTypeOutput(user.getUserType())
        );
    }

    @Override
    public List<GetUserOutputDTO> toGetOutputList(List<User> users) {
        return users.stream().map(this::toGetOutput).collect(Collectors.toList());
    }

    private GetUserTypeOutputDTO toUserTypeOutput(UserType userType) {
        return userType != null
                ? new GetUserTypeOutputDTO(
                        userType.getId(),
                        userType.getName(),
                        userType.getSubType())
                : null;
    }

    private User buildUser(UUID id, String name, String email, String password, UserType userType) {
        return id == null
                ? new User(name, email, password, userType)
                : new User(id, name, email, password, userType);
    }

}
