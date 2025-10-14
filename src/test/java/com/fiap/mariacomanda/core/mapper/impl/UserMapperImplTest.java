package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.output.CreateUserOutputDTO;
import com.fiap.mariacomanda.core.dto.user.output.GetUserOutputDTO;
import com.fiap.mariacomanda.core.dto.usertype.output.GetUserTypeOutputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {

    private UserMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear CreateUserInputDTO e UserType para User corretamente")
    void deveMapearCreateUserInputDTOParaUser() {
        UUID userTypeId = UUID.randomUUID();
        UserType userType = new UserType(userTypeId, "CUSTOMER", "CUSTOMER");
        CreateUserInputDTO dto = new CreateUserInputDTO(
                "João Silva",
                "joao@email.com",
                "senha123",
                userTypeId
        );

        User user = mapper.toDomain(dto, userType);

        assertNull(user.getId());
        assertEquals(dto.name(), user.getName());
        assertEquals(dto.email().toLowerCase(), user.getEmail());
        assertEquals(dto.password(), user.getPasswordHash());
        assertEquals(userType, user.getUserType());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserInputDTO e UserType para User corretamente")
    void deveMapearUpdateUserInputDTOParaUser() {
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();
        UserType userType = new UserType(userTypeId, "OWNER", "OWNER");
        UpdateUserInputDTO dto = new UpdateUserInputDTO(
                userId,
                "Maria Souza",
                "maria@email.com",
                "novaSenha",
                userTypeId
        );

        User user = mapper.toDomain(dto, userType);

        assertEquals(userId, user.getId());
        assertEquals(dto.name(), user.getName());
        assertEquals(dto.email().toLowerCase(), user.getEmail());
        assertEquals(dto.password(), user.getPasswordHash());
        assertEquals(userType, user.getUserType());
    }

    @Test
    @DisplayName("Deve mapear User para CreateUserOutputDTO corretamente")
    void deveMapearUserParaCreateUserOutputDTO() {
        UUID id = UUID.randomUUID();
        UserType userType = new UserType(UUID.randomUUID(), "OWNER", "OWNER");
        User user = new User(id, "Maria", "maria@email.com", "senha", userType);

        CreateUserOutputDTO dto = mapper.toCreateOutput(user);

        assertEquals(user.getId(), dto.id());
        assertTrue(dto.message().contains("OWNER"));
    }

    @Test
    @DisplayName("Deve mapear User para GetUserOutputDTO corretamente")
    void deveMapearUserParaGetUserOutputDTO() {
        UUID id = UUID.randomUUID();
        UserType userType = new UserType(UUID.randomUUID(), "CUSTOMER", "CUSTOMER");
        User user = new User(id, "Paulo", "paulo@email.com", "senha", userType);

        GetUserOutputDTO dto = mapper.toGetOutput(user);

        assertEquals(user.getId(), dto.id());
        assertEquals(user.getName(), dto.name());
        assertEquals(user.getEmail(), dto.email());
        assertNotNull(dto.userType());
        assertEquals(user.getUserType().getId(), dto.userType().id());
        assertEquals(user.getUserType().getName(), dto.userType().name());
        assertEquals(user.getUserType().getSubType(), dto.userType().subType());
    }

    @Test
    @DisplayName("Deve mapear lista de User para lista de GetUserOutputDTO corretamente")
    void deveMapearListaUserParaListaGetUserOutputDTO() {
        UserType type = new UserType(UUID.randomUUID(), "CUSTOMER", "CUSTOMER");
        User u1 = new User(UUID.randomUUID(), "Nome1", "a@exemplo.com", "senha1", type);
        User u2 = new User(UUID.randomUUID(), "Nome2", "b@exemplo.com", "senha2", type);

        List<GetUserOutputDTO> list = mapper.toGetOutputList(List.of(u1, u2));

        assertEquals(2, list.size());
        assertEquals(u1.getId(), list.get(0).id());
        assertEquals(u2.getId(), list.get(1).id());
        assertEquals(u1.getName(), list.get(0).name());
        assertEquals(u2.getName(), list.get(1).name());
    }

}
