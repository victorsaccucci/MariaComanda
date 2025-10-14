package com.fiap.mariacomanda.infrastructure.database.mapper.user.impl;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import com.fiap.mariacomanda.infrastructure.database.mapper.user.impl.UserEntityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperImplTest {

    private UserEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserEntityMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear User (domain) para UserEntity corretamente")
    void deveMapearDomainParaEntityCorretamente() {
        UUID userTypeId = UUID.randomUUID();
        UserType userType = new UserType(userTypeId, "Admin", "Owner");

        User user = new User(
                UUID.randomUUID(),
                "Maria Silva",
                "maria@example.com",
                "senha123",
                userType
        );

        UserEntity entity = mapper.toEntity(user);

        assertNotNull(entity);
        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getName(), entity.getName());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getPasswordHash(), entity.getPasswordHash());
        assertEquals(userTypeId, entity.getUserTypeId());
    }

    @Test
    @DisplayName("Deve mapear UserEntity para User (domain) corretamente")
    void deveMapearEntityParaDomainCorretamente() {
        UUID userTypeId = UUID.randomUUID();
        UserType userType = new UserType(userTypeId, "Customer", "CUSTOMER");

        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("João Santos");
        entity.setEmail("joao@example.com");
        entity.setPasswordHash("hash123");
        entity.setUserTypeId(userTypeId);

        User user = mapper.toDomain(entity, userType);

        assertNotNull(user);
        assertEquals(entity.getId(), user.getId());
        assertEquals(entity.getName(), user.getName());
        assertEquals(entity.getEmail(), user.getEmail());
        assertEquals(entity.getPasswordHash(), user.getPasswordHash());
        assertEquals(userType, user.getUserType());
        assertEquals(userTypeId, user.getUserType().getId());
    }
}
