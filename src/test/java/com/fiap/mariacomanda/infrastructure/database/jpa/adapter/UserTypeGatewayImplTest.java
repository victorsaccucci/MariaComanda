package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.UserTypeJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.impl.UserTypeEntityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeGatewayImplTest {

    private UserTypeJpaRepository userTypeJpaRepository;
    private UserTypeEntityMapperImpl userTypeEntityMapperImpl;
    private UserTypeGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        userTypeJpaRepository = mock(UserTypeJpaRepository.class);
        userTypeEntityMapperImpl = mock(UserTypeEntityMapperImpl.class);
        gateway = new UserTypeGatewayImpl(userTypeJpaRepository, userTypeEntityMapperImpl);
    }

    @Test
    @DisplayName("Deve salvar UserType corretamente")
    void testSaveUserType() {
        UserType userType = new UserType(UUID.randomUUID(), "Admin", "OWNER");
        UserTypeEntity entity = new UserTypeEntity();
        entity.setId(UUID.randomUUID());

        when(userTypeEntityMapperImpl.toEntity(userType)).thenReturn(entity);
        when(userTypeJpaRepository.save(entity)).thenReturn(entity);
        when(userTypeEntityMapperImpl.toDomain(entity)).thenReturn(userType);

        UserType result = gateway.save(userType);

        assertNotNull(result);
        assertEquals(userType, result);
        verify(userTypeJpaRepository).save(entity);
    }

    @Test
    @DisplayName("Deve buscar UserType por ID")
    void testFindById() {
        UUID id = UUID.randomUUID();
        UserTypeEntity entity = new UserTypeEntity();
        UserType userType = new UserType(id, "Admin", "OWNER");

        when(userTypeJpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userTypeEntityMapperImpl.toDomain(entity)).thenReturn(userType);

        Optional<UserType> result = gateway.findById(id);

        assertTrue(result.isPresent());
        assertEquals(userType, result.get());
        verify(userTypeJpaRepository).findById(id);
    }

    @Test
    @DisplayName("Deve retornar lista de UserTypes")
    void testFindAll() {
        UserTypeEntity entity = new UserTypeEntity();
        UserType userType = new UserType(UUID.randomUUID(), "Employee", "CUSTOMER");

        when(userTypeJpaRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(userTypeEntityMapperImpl.toDomain(entity)).thenReturn(userType);

        List<UserType> result = gateway.findAll(0, 10);

        assertEquals(1, result.size());
        assertEquals(userType, result.get(0));
        verify(userTypeJpaRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    @DisplayName("Deve deletar UserType por ID")
    void testDeleteById() {
        UUID id = UUID.randomUUID();

        gateway.deleteById(id);

        verify(userTypeJpaRepository).deleteById(id);
    }
}
