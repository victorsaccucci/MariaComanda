package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO;
import com.fiap.mariacomanda.core.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserUseCaseImplTest {

    private UserGateway userGateway;
    private UserTypeGateway userTypeGateway;
    private UserMapper userMapper;
    private CreateUserUseCaseImpl useCase;

    private UUID requesterId;
    private UUID userTypeId;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        userTypeGateway = mock(UserTypeGateway.class);
        userMapper = mock(UserMapper.class);
        useCase = new CreateUserUseCaseImpl(userGateway, userTypeGateway, userMapper);

        requesterId = UUID.randomUUID();
        userTypeId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso quando todos os dados forem válidos")
    void deveCriarUsuarioComSucesso() {
        CreateUserInputDTO inputDTO = new CreateUserInputDTO("John Doe", "john@test.com", "senha", userTypeId);
        UserType userType = new UserType(userTypeId, "USER", UserType.OWNER);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        User newUser = new User(UUID.randomUUID(), inputDTO.name(), inputDTO.email(), inputDTO.password(), userType);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userTypeGateway.findById(userTypeId)).thenReturn(Optional.of(userType));
        when(userMapper.toDomain(inputDTO, userType)).thenReturn(newUser);
        when(userGateway.save(newUser)).thenReturn(newUser);

        User result = useCase.execute(inputDTO, requesterId);

        assertNotNull(result);
        assertEquals(inputDTO.name(), result.getName());
        assertEquals(inputDTO.email(), result.getEmail());
        verify(userGateway).save(newUser);
    }

    @Test
    @DisplayName("Deve lançar exceção quando inputDTO for nulo")
    void deveLancarExcecaoInputNulo() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> useCase.execute(null, requesterId));
        assertEquals("com.fiap.mariacomanda.core.dto.user.input.CreateUserInputDTO cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for encontrado")
    void deveLancarExcecaoRequesterNaoEncontrado() {
        CreateUserInputDTO inputDTO = new CreateUserInputDTO("John", "john@test.com", "senha", userTypeId);
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(inputDTO, requesterId));
        assertEquals("Requester user not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando name for vazio")
    void deveLancarExcecaoNameVazio() {
        CreateUserInputDTO inputDTO = new CreateUserInputDTO(" ", "john@test.com", "senha", userTypeId);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(inputDTO, requesterId));
        assertEquals("User name cannot be empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando UserType não encontrado")
    void deveLancarExcecaoUserTypeNaoEncontrado() {
        CreateUserInputDTO inputDTO = new CreateUserInputDTO("John", "john@test.com", "senha", userTypeId);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userTypeGateway.findById(userTypeId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(inputDTO, requesterId));
        assertEquals("UserType not found for id: " + userTypeId, ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando email for vazio")
    void deveLancarExcecaoEmailVazio() {
        CreateUserInputDTO inputDTO = new CreateUserInputDTO("John", "   ", "senha123", userTypeId);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(inputDTO, requesterId));
        assertEquals("User email cannot be empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando password for vazio")
    void deveLancarExcecaoPasswordVazio() {
        CreateUserInputDTO inputDTO = new CreateUserInputDTO("John", "john@test.com", "   ", userTypeId);
        User requester = new User(requesterId, "Owner", "owner@test.com", "senha", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(inputDTO, requesterId));
        assertEquals("User password cannot be empty", ex.getMessage());
    }

}
