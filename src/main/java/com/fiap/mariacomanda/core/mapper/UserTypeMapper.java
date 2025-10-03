package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;

import java.util.List;

public interface UserTypeMapper {

    UserType toDomain(CreateUserTypeInputDTO dto);

    UserType toDomain(UpdateUserTypeInputDTO dto);

    CreateUserTypeOutputDTO toCreateOutput(UserType userType);

    GetUserTypeOutputDTO toGetOutput(UserType userType);

    List<GetUserTypeOutputDTO> toGetOutputList(List<UserType> userTypes);

}
