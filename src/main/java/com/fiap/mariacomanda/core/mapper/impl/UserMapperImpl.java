package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toDomain(CreateUserInputDTO dto) {
        return new User(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.password(), dto.userTypeId());
    }

    @Override
    public User toDomain(UpdateUserInputDTO dto) {
        return new User(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.password(),
                dto.userTypeId()
        );
    }

    @Override
    public CreateUserOutputDTO toCreateOutput(User user) {
        return new CreateUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserTypeId() != null ? user.getUserTypeId() : null
        );
    }

    @Override
    public GetUserOutputDTO toGetOutput(User user) {
        return new GetUserOutputDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserTypeId() != null ? user.getUserTypeId() : null
        );
    }

    @Override
    public List<GetUserOutputDTO> toGetOutputList(List<User> users) {
        return users.stream().map(this::toGetOutput).collect(Collectors.toList());
    }
}
