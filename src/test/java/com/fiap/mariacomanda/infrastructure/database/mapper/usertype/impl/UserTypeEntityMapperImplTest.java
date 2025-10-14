package com.fiap.mariacomanda.infrastructure.database.mapper.usertype.impl;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.impl.UserTypeEntityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeEntityMapperImplTest {

    private UserTypeEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserTypeEntityMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear UserType (domain) para UserTypeEntity corretamente")
    void deveMapearDomainParaEntityCorretamente() {
        UserType domain = new UserType(UUID.randomUUID(), "Admin", "CUSTOMER");

        UserTypeEntity entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getSubType(), entity.getSubType());
    }

    @Test
    @DisplayName("Deve mapear UserTypeEntity para UserType (domain) corretamente com ID")
    void deveMapearEntityParaDomainComIdCorretamente() {
        UUID id = UUID.randomUUID();
        UserTypeEntity entity = new UserTypeEntity();
        entity.setId(id);
        entity.setName("Customer");
        entity.setSubType("CUSTOMER");

        UserType domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getSubType(), domain.getSubType());
    }

    @Test
    @DisplayName("Deve mapear UserTypeEntity para UserType (domain) corretamente sem ID")
    void deveMapearEntityParaDomainSemIdCorretamente() {
        UserTypeEntity entity = new UserTypeEntity();
        entity.setId(null);
        entity.setName("Guest");
        entity.setSubType("CUSTOMER");

        UserType domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertNull(domain.getId(), "Mesmo sem ID, o mapper deve criar um UserType válido");
        assertEquals("Guest", domain.getName());
        assertEquals("CUSTOMER", domain.getSubType());
    }
}
