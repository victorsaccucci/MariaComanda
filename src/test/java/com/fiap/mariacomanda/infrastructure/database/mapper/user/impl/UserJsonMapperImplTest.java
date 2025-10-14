package com.fiap.mariacomanda.infrastructure.database.mapper.user.impl;

import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.dto.user.input.UpdateUserInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserJsonMapperImplTest {

    private UserJsonMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserJsonMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear CreateUserJson para CreateUserInputDTO corretamente")
    void deveMapearCreateUserJsonParaCreateUserInputDTO() throws Exception {
        CreateUserJson json = new CreateUserJson();

        // Usando reflexão para preencher campos privados
        setPrivateField(json, "name", "Maria");
        setPrivateField(json, "email", "maria@test.com");
        setPrivateField(json, "password", "senha123");
        setPrivateField(json, "userTypeId", UUID.randomUUID());

        CreateUserInputDTO dto = mapper.toCreateInput(json);

        assertEquals("Maria", dto.name());
        assertEquals("maria@test.com", dto.email());
        assertEquals("senha123", dto.password());
        assertNotNull(dto.userTypeId());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserJson para UpdateUserInputDTO corretamente")
    void deveMapearUpdateUserJsonParaUpdateUserInputDTO() throws Exception {
        UpdateUserJson json = new UpdateUserJson();
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        setPrivateField(json, "name", "João");
        setPrivateField(json, "email", "joao@test.com");
        setPrivateField(json, "password", "senha456");
        setPrivateField(json, "userTypeId", userTypeId);

        UpdateUserInputDTO dto = mapper.toUpdateInput(userId, json);

        assertEquals(userId, dto.id());
        assertEquals("João", dto.name());
        assertEquals("joao@test.com", dto.email());
        assertEquals("senha456", dto.password());
        assertEquals(userTypeId, dto.userTypeId());
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
