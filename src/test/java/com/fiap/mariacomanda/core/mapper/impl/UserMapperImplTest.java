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
    private UserType sampleUserType;

    @BeforeEach
    void setUp() {
        mapper = new UserMapperImpl();
        sampleUserType = new UserType(UUID.randomUUID(), "ADMIN", "OWNER");
    }

    @Test
    @DisplayName("Deve mapear CreateUserInputDTO para User corretamente")
    void deveMapearCreateUserInputDTOParaUser() {
        CreateUserInputDTO dto = new CreateUserInputDTO(
                "João Silva",
                "joao@example.com",
                "hashedPassword123",
                sampleUserType.getId()
        );

        User user = mapper.toDomain(dto, sampleUserType);

        assertNull(user.getId()); // criação não define ID
        assertEquals(dto.name(), user.getName());
        assertEquals(dto.email(), user.getEmail());
        assertEquals(dto.password(), user.getPasswordHash());
        assertEquals(sampleUserType, user.getUserType());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserInputDTO para User corretamente")
    void deveMapearUpdateUserInputDTOParaUser() {
        UUID userId = UUID.randomUUID();
        UpdateUserInputDTO dto = new UpdateUserInputDTO(
                userId,
                "Maria Souza",
                "maria@example.com",
                "novaSenha123",
                sampleUserType.getId()
        );

        User user = mapper.toDomain(dto, sampleUserType);

        assertEquals(dto.id(), user.getId());
        assertEquals(dto.name(), user.getName());
        assertEquals(dto.email(), user.getEmail());
        assertEquals(dto.password(), user.getPasswordHash());
        assertEquals(sampleUserType, user.getUserType());
    }

    @Test
    @DisplayName("Deve mapear User para CreateUserOutputDTO corretamente")
    void deveMapearUserParaCreateUserOutputDTO() {
        User user = new User(
                UUID.randomUUID(),
                "Carlos Lima",
                "carlos@example.com",
                "senha123",
                sampleUserType
        );

        CreateUserOutputDTO dto = mapper.toCreateOutput(user);

        assertEquals(user.getId(), dto.id());
        assertEquals(user.getName(), dto.name());
        assertEquals(user.getEmail(), dto.email());

        GetUserTypeOutputDTO userTypeDto = dto.userType();
        assertNotNull(userTypeDto);
        assertEquals(sampleUserType.getId(), userTypeDto.id());
        assertEquals(sampleUserType.getName(), userTypeDto.name());
        assertEquals(sampleUserType.getSubType(), userTypeDto.subType());
    }

    @Test
    @DisplayName("Deve mapear User para GetUserOutputDTO corretamente")
    void deveMapearUserParaGetUserOutputDTO() {
        User user = new User(
                UUID.randomUUID(),
                "Ana Pereira",
                "ana@example.com",
                "senha456",
                sampleUserType
        );

        GetUserOutputDTO dto = mapper.toGetOutput(user);

        assertEquals(user.getId(), dto.id());
        assertEquals(user.getName(), dto.name());
        assertEquals(user.getEmail(), dto.email());

        GetUserTypeOutputDTO userTypeDto = dto.userType();
        assertNotNull(userTypeDto);
        assertEquals(sampleUserType.getId(), userTypeDto.id());
        assertEquals(sampleUserType.getName(), userTypeDto.name());
        assertEquals(sampleUserType.getSubType(), userTypeDto.subType());
    }

    @Test
    @DisplayName("Deve mapear lista de User para lista de GetUserOutputDTO corretamente")
    void deveMapearListaDeUserParaListaDeGetUserOutputDTO() {
        User user1 = new User(
                UUID.randomUUID(),
                "User1",
                "user1@example.com",
                "senha1",
                sampleUserType
        );

        User user2 = new User(
                UUID.randomUUID(),
                "User2",
                "user2@example.com",
                "senha2",
                sampleUserType
        );

        List<GetUserOutputDTO> dtoList = mapper.toGetOutputList(List.of(user1, user2));

        assertEquals(2, dtoList.size());
        assertTrue(dtoList.stream().anyMatch(dto -> dto.name().equals("User1")));
        assertTrue(dtoList.stream().anyMatch(dto -> dto.name().equals("User2")));
    }

    @Test
    @DisplayName("Deve retornar null para UserType nulo")
    void deveRetornarNullParaUserTypeNulo() {
        assertNull(mapper.toUserTypeOutput(null));
    }

}
