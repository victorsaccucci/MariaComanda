package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserTypeMapperImpl implements UserTypeMapper {

    @Override
    public UserType mapCreate(CreateUserTypeInputDTO dto) {
        return new UserType(dto.id(), dto.typeName());
    }

    @Override
    public UserType mapUpdate(UpdateUserTypeInputDTO dto) {
        return new UserType(dto.id(), dto.typeName());
    }

    @Override
    public CreateUserTypeOutputDTO mapCreate(UserType user) {
        return new CreateUserTypeOutputDTO(user.getId(), user.getTypeName());
    }

    @Override
    public GetUserTypeOutputDTO mapToGetOutputDTO(UserType user) {
        return new GetUserTypeOutputDTO(user.getId(), user.getTypeName());
    }

    @Override
    public List<GetUserTypeOutputDTO> mapToGetOutputDTOList(List<UserType> users) {
        return users.stream().map(this::mapToGetOutputDTO).collect(Collectors.toList());
    }
}
