package com.fiap.mariacomanda.infrastructure.database.mapper.usertype.impl;

import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.dto.usertype.input.UpdateUserTypeInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateUserTypeJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateUserTypeJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTypeJsonMapperImplTest {

    private UserTypeJsonMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserTypeJsonMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear CreateUserTypeJson para CreateUserTypeInputDTO corretamente")
    void deveMapearCreateUserTypeJsonParaCreateInputDTO() throws Exception {
        CreateUserTypeJson json = new CreateUserTypeJson();

        setPrivateField(json, "name", "Administrador");
        setPrivateField(json, "subType", "OWNER");

        CreateUserTypeInputDTO dto = mapper.toCreateInput(json);

        assertEquals("Administrador", dto.name());
        assertEquals("OWNER", dto.subType());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserTypeJson para UpdateUserTypeInputDTO corretamente")
    void deveMapearUpdateUserTypeJsonParaUpdateInputDTO() throws Exception {
        UpdateUserTypeJson json = new UpdateUserTypeJson();
        UUID id = UUID.randomUUID();

        setPrivateField(json, "name", "Cliente");
        setPrivateField(json, "subType", "CUSTOMER");

        UpdateUserTypeInputDTO dto = mapper.toUpdateInput(id, json);

        assertEquals(id, dto.id());
        assertEquals("Cliente", dto.name());
        assertEquals("CUSTOMER", dto.subType());
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
