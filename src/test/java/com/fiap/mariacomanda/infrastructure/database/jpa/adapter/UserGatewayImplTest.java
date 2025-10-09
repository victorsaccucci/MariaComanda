package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.UserJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.UserTypeJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.user.impl.UserEntityMapperImpl;
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

class UserGatewayImplTest {

    private UserJpaRepository userJpaRepository;
    private UserEntityMapperImpl userEntityMapperImpl;
    private UserTypeJpaRepository userTypeJpaRepository;
    private UserTypeEntityMapperImpl userTypeEntityMapperImpl;
    private UserGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        userJpaRepository = mock(UserJpaRepository.class);
        userEntityMapperImpl = mock(UserEntityMapperImpl.class);
        userTypeJpaRepository = mock(UserTypeJpaRepository.class);
        userTypeEntityMapperImpl = mock(UserTypeEntityMapperImpl.class);
        gateway = new UserGatewayImpl(userJpaRepository, userEntityMapperImpl, userTypeJpaRepository, userTypeEntityMapperImpl);
    }

    @Test
    @DisplayName("Deve salvar User corretamente")
    void testSaveUser() {
        UserType userType = new UserType(UUID.randomUUID(), "Admin", "OWNER");
        User user = new User(UUID.randomUUID(), "João", "joao@email.com", "123", userType);

        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());

        when(userEntityMapperImpl.toEntity(user)).thenReturn(entity);
        when(userJpaRepository.save(entity)).thenReturn(entity);
        when(userEntityMapperImpl.toDomain(entity, userType)).thenReturn(user);

        User result = gateway.save(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userJpaRepository).save(entity);
    }

    @Test
    @DisplayName("Deve buscar User por ID com UserType válido")
    void testFindById() {
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUserTypeId(userTypeId);

        UserTypeEntity userTypeEntity = new UserTypeEntity();
        userTypeEntity.setId(userTypeId);

        UserType userType = new UserType(userTypeId, "Admin", "OWNER");
        User user = new User(userId, "Maria", "maria@email.com", "senha", userType);

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userTypeJpaRepository.findById(userTypeId)).thenReturn(Optional.of(userTypeEntity));
        when(userTypeEntityMapperImpl.toDomain(userTypeEntity)).thenReturn(userType);
        when(userEntityMapperImpl.toDomain(userEntity, userType)).thenReturn(user);

        Optional<User> result = gateway.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userJpaRepository).findById(userId);
        verify(userTypeJpaRepository).findById(userTypeId);
    }

    @Test
    @DisplayName("Deve lançar exceção se UserType não for encontrado ao buscar por ID")
    void testFindByIdUserTypeNotFound() {
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUserTypeId(userTypeId);

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userTypeJpaRepository.findById(userTypeId)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> gateway.findById(userId));

        assertEquals("UserType not found for id " + userTypeId, exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de Users")
    void testFindAll() {
        UUID userTypeId = UUID.randomUUID();

        UserEntity entity = new UserEntity();
        entity.setUserTypeId(userTypeId);

        UserTypeEntity userTypeEntity = new UserTypeEntity();
        userTypeEntity.setId(userTypeId);

        UserType userType = new UserType(userTypeId, "Admin", "OWNER");
        User user = new User(UUID.randomUUID(), "Pedro", "pedro@email.com", "senha", userType);

        when(userJpaRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(userTypeJpaRepository.findById(userTypeId)).thenReturn(Optional.of(userTypeEntity));
        when(userTypeEntityMapperImpl.toDomain(userTypeEntity)).thenReturn(userType);
        when(userEntityMapperImpl.toDomain(entity, userType)).thenReturn(user);

        List<User> result = gateway.findAll(0, 10);

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    @DisplayName("Deve deletar User por ID")
    void testDeleteById() {
        UUID id = UUID.randomUUID();

        gateway.deleteById(id);

        verify(userJpaRepository).deleteById(id);
    }
}
