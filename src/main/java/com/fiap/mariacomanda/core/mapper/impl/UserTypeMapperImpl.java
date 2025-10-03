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
    public UserType toDomain(CreateUserTypeInputDTO dto) {
        return new UserType(dto.id(), dto.name(), dto.subType());
    }

    @Override
    public UserType toDomain(UpdateUserTypeInputDTO dto) {
        return new UserType(dto.id(), dto.name(), dto.subType());
    }

    @Override
    public CreateUserTypeOutputDTO toCreateOutput(UserType userType) {
        return new CreateUserTypeOutputDTO(userType.getId(), userType.getName(), userType.getSubType());
    }

    @Override
    public GetUserTypeOutputDTO toGetOutput(UserType userType) {
        return new GetUserTypeOutputDTO(userType.getId(), userType.getName(), userType.getSubType());
    }

    @Override
    public List<GetUserTypeOutputDTO> toGetOutputList(List<UserType> userTypes) {
        return userTypes.stream().map(this::toGetOutput).collect(Collectors.toList());
    }
}
