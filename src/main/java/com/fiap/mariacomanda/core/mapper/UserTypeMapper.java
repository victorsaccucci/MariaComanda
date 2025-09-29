package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;

import java.util.List;

public interface UserTypeMapper {

    UserType mapCreate(CreateUserTypeInputDTO dto);

    UserType mapUpdate(UpdateUserTypeInputDTO dto);

    CreateUserTypeOutputDTO mapCreate(UserType user);

    GetUserTypeOutputDTO mapToGetOutputDTO(UserType user);

    List<GetUserTypeOutputDTO> mapToGetOutputDTOList(List<UserType> users);

}
