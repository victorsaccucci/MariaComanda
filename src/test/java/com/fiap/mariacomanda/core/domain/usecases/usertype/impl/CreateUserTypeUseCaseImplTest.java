package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserTypeUseCaseImplTest {

    private UserTypeGateway userTypeGateway;
    private UserGateway userGateway;
    private UserTypeMapper userTypeMapper;
    private CreateUserTypeUseCaseImpl useCase;

    private UUID requesterId;

    @BeforeEach
    void setUp() {
        userTypeGateway = mock(UserTypeGateway.class);
        userGateway = mock(UserGateway.class);
        userTypeMapper = mock(UserTypeMapper.class);
        useCase = new CreateUserTypeUseCaseImpl(userTypeGateway, userGateway, userTypeMapper);

        requesterId = UUID.randomUUID();

    }

    UserType type = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
    User requester = new User(UUID.randomUUID(), "Owner", "owner@test.com", "pass", type);

    @Test
    @DisplayName("Deve criar UserType com sucesso")
    void deveCriarUserTypeComSucesso() {
        CreateUserTypeInputDTO input = new CreateUserTypeInputDTO("Admin", "OWNER");
        UserType domain = new UserType(UUID.randomUUID(), "Admin", "OWNER");

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userTypeMapper.toDomain(input)).thenReturn(domain);
        when(userTypeGateway.save(domain)).thenReturn(domain);

        UserType result = useCase.execute(input, requesterId);

        assertNotNull(result);
        assertEquals("Admin", result.getName());
        verify(userGateway).findById(requesterId);
        verify(userTypeGateway).save(domain);
    }

    @Test
    @DisplayName("Deve lançar exceção quando inputDTO for nulo")
    void deveLancarExcecaoInputDTO() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> useCase.execute(null, requesterId));
        assertEquals("com.fiap.mariacomanda.core.dto.usertype.input.CreateUserTypeInputDTO cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não existir")
    void deveLancarExcecaoRequesterNaoEncontrado() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        CreateUserTypeInputDTO input = new CreateUserTypeInputDTO("Admin", "OWNER");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(input, requesterId));
        assertEquals("Requester user not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando name for vazio")
    void deveLancarExcecaoNameVazio() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        CreateUserTypeInputDTO input = new CreateUserTypeInputDTO(" ", "OWNER");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(input, requesterId));
        assertEquals("User type name cannot be empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando subType for inválido")
    void deveLancarExcecaoSubTypeInvalido() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        CreateUserTypeInputDTO input = new CreateUserTypeInputDTO("Admin", "INVALID");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(input, requesterId));
        assertEquals("User type subType must be CUSTOMER or OWNER", ex.getMessage());
    }
}
