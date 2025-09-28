package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUsersInputDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapperImpl {

    public User mapCreateInputToDomain(CreateUserInputDTO dto) {
        return new User(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.password(),
                null
        );
    }

    public CreateUserOutputDTO mapCreateDomainToOutput(User user) {
        return new CreateUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserType() != null ? user.getUserType().getId() : null
        );
    }

    public GetUserOutputDTO mapGetDomainToOutput(User user) {
        return new GetUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserType() != null ? user.getUserType().getId() : null
        );
    }

    public List<GetUserOutputDTO> mapGetUserOutputDtoToList(List<User> users) {
        return users.stream().map(this::mapGetDomainToOutput).collect(Collectors.toList());
    }

    public User mapUpdateInputToDomain(UpdateUsersInputDTO dto) {
        return new User(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.password(),
                null
        );
    }
}
