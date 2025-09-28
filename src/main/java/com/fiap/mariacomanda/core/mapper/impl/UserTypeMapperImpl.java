package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.usertype.UserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.TypeUserOutputDTO;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserTypeMapperImpl implements UserTypeMapper {

    @Override
    public UserType toDomain(UserTypeInputDTO dto) {
        return new UserType(
                dto.id(),
                dto.typeName()
        );
    }

    @Override
    public TypeUserOutputDTO toCreateOutput(UserType userType) {
        return new TypeUserOutputDTO(
                userType.getId(),
                userType.getTypeName()
        );
    }

    @Override
    public TypeUserOutputDTO toGetOutput(UserType userType) {
        return new TypeUserOutputDTO(
                userType.getId(),
                userType.getTypeName()
        );
    }

    @Override
    public List<TypeUserOutputDTO> toGetOutputList(List<UserType> userTypes) {
        return userTypes.stream().map(this::toGetOutput).collect(Collectors.toList());
    }
}
