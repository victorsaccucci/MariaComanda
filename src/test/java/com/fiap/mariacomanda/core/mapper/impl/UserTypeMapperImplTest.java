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

import static org.junit.jupiter.api.Assertions.*;

class UserTypeMapperImplTest {

    private UserTypeMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserTypeMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear CreateUserTypeInputDTO para UserType corretamente")
    void deveMapearCreateUserTypeInputDTOParaUserType() {
        CreateUserTypeInputDTO dto = new CreateUserTypeInputDTO(
                "Admin",
                "OWNER"
        );

        UserType userType = mapper.toDomain(dto);

        assertNull(userType.getId());
        assertEquals(dto.name(), userType.getName());
        assertEquals(dto.subType(), userType.getSubType());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserTypeInputDTO para UserType corretamente")
    void deveMapearUpdateUserTypeInputDTOParaUserType() {
        UUID id = UUID.randomUUID();
        UpdateUserTypeInputDTO dto = new UpdateUserTypeInputDTO(
                id,
                "Manager",
                "OWNER"
        );

        UserType userType = mapper.toDomain(dto);

        assertEquals(dto.id(), userType.getId());
        assertEquals(dto.name(), userType.getName());
        assertEquals(dto.subType(), userType.getSubType());
    }

    @Test
    @DisplayName("Deve mapear UserType para CreateUserTypeOutputDTO corretamente")
    void deveMapearUserTypeParaCreateUserTypeOutputDTO() {
        UserType userType = new UserType(
                UUID.randomUUID(),
                "Admin",
                "Owner"
        );

        CreateUserTypeOutputDTO dto = mapper.toCreateOutput(userType);

        assertEquals(userType.getId(), dto.id());
        assertEquals(userType.getName(), dto.name());
        assertEquals(userType.getSubType(), dto.subType());
    }

    @Test
    @DisplayName("Deve mapear UserType para GetUserTypeOutputDTO corretamente")
    void deveMapearUserTypeParaGetUserTypeOutputDTO() {
        UserType userType = new UserType(
                UUID.randomUUID(),
                "Manager",
                "OWNER"
        );

        GetUserTypeOutputDTO dto = mapper.toGetOutput(userType);

        assertEquals(userType.getId(), dto.id());
        assertEquals(userType.getName(), dto.name());
        assertEquals(userType.getSubType(), dto.subType());
    }

    @Test
    @DisplayName("Deve mapear lista de UserType para lista de GetUserTypeOutputDTO corretamente")
    void deveMapearListaDeUserTypeParaListaDeGetUserTypeOutputDTO() {
        UserType ut1 = new UserType(UUID.randomUUID(), "Admin", "Owner");
        UserType ut2 = new UserType(UUID.randomUUID(), "Manager", "OWNER");

        List<GetUserTypeOutputDTO> dtoList = mapper.toGetOutputList(List.of(ut1, ut2));

        assertEquals(2, dtoList.size());
        assertTrue(dtoList.stream().anyMatch(dto -> dto.name().equals("Admin")));
        assertTrue(dtoList.stream().anyMatch(dto -> dto.name().equals("Manager")));
    }
}
