package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.usertype.UserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.TypeUserOutputDTO;

import java.util.List;

public interface UserTypeMapper {

    // Create
    UserType toDomain(UserTypeInputDTO dto);
    TypeUserOutputDTO toCreateOutput(UserType userType);

    // Get
    TypeUserOutputDTO toGetOutput(UserType userType);
    List<TypeUserOutputDTO> toGetOutputList(List<UserType> userTypes);
}
