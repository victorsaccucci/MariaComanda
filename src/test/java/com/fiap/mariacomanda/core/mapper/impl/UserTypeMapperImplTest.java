package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.CreateUserTypeOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTypeMapperImplTest {

    private UserTypeMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserTypeMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear CreateUserTypeInputDTO para UserType corretamente")
    void deveMapearCreateUserTypeInputDTOParaUserType() {
        CreateUserTypeInputDTO dto = new CreateUserTypeInputDTO("cliente", "CUSTOMER");

        UserType userType = mapper.toDomain(dto);

        assertNull(userType.getId());
        assertEquals(dto.name(), userType.getName());
        assertEquals(dto.subType(), userType.getSubType());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserTypeInputDTO para UserType corretamente")
    void deveMapearUpdateUserTypeInputDTOParaUserType() {
        UUID id = UUID.randomUUID();
        UpdateUserTypeInputDTO dto = new UpdateUserTypeInputDTO(id, "owner", "OWNER");

        UserType userType = mapper.toDomain(dto);

        assertEquals(dto.id(), userType.getId());
        assertEquals(dto.name(), userType.getName());
        assertEquals(dto.subType(), userType.getSubType());
    }

    @Test
    @DisplayName("Deve mapear UserType para CreateUserTypeOutputDTO corretamente")
    void deveMapearUserTypeParaCreateUserTypeOutputDTO() {
        UUID id = UUID.randomUUID();
        UserType userType = new UserType(id, "cliente", "CUSTOMER");

        CreateUserTypeOutputDTO dto = mapper.toCreateOutput(userType);

        assertEquals(userType.getId(), dto.id());
    }

    @Test
    @DisplayName("Deve mapear UserType para GetUserTypeOutputDTO corretamente")
    void deveMapearUserTypeParaGetUserTypeOutputDTO() {
        UUID id = UUID.randomUUID();
        UserType userType = new UserType(id, "owner", "OWNER");

        GetUserTypeOutputDTO dto = mapper.toGetOutput(userType);

        assertEquals(userType.getId(), dto.id());
        assertEquals(userType.getName(), dto.name());
        assertEquals(userType.getSubType(), dto.subType());
    }

    @Test
    @DisplayName("Deve mapear lista de UserType para lista de GetUserTypeOutputDTO corretamente")
    void deveMapearListaDeUserTypeParaListaDeGetUserTypeOutputDTO() {
        UserType type1 = new UserType(UUID.randomUUID(), "cliente", "CUSTOMER");
        UserType type2 = new UserType(UUID.randomUUID(), "dono", "OWNER");

        List<GetUserTypeOutputDTO> dtos = mapper.toGetOutputList(List.of(type1, type2));

        assertEquals(2, dtos.size());
        assertEquals(type1.getName(), dtos.get(0).name());
        assertEquals(type2.getName(), dtos.get(1).name());
    }
}
