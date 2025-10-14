package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserTypeUseCaseImplTest {

    private UserTypeGateway userTypeGateway;
    private UserGateway userGateway;
    private UpdateUserTypeUseCaseImpl useCase;

    private UUID userTypeId;
    private UUID requesterId;

    @BeforeEach
    void setUp() {
        userTypeGateway = mock(UserTypeGateway.class);
        userGateway = mock(UserGateway.class);
        useCase = new UpdateUserTypeUseCaseImpl(userTypeGateway, userGateway);

        userTypeId = UUID.randomUUID();
        requesterId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve atualizar UserType com sucesso")
    void deveAtualizarUserTypeComSucesso() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        UserType userType = new UserType(userTypeId, "ADMIN", UserType.OWNER);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userTypeGateway.findById(userTypeId)).thenReturn(Optional.of(userType));
        when(userTypeGateway.save(userType)).thenReturn(userType);

        UserType result = useCase.execute(userType, requesterId);

        assertEquals(userType, result);
        verify(userTypeGateway).save(userType);
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for encontrado")
    void deveLancarExcecaoRequesterNaoEncontrado() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(new UserType(userTypeId, "OWNER", UserType.OWNER), requesterId));
        assertEquals("Requester user not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando UserType não for encontrado")
    void deveLancarExcecaoUserTypeNaoEncontrado() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userTypeGateway.findById(userTypeId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(new UserType(userTypeId, "OWNER", UserType.OWNER), requesterId));
        assertEquals("UserType not found for id: " + userTypeId, ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando UserType passado for nulo")
    void deveLancarExcecaoUserTypeNulo() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> useCase.execute(null, requesterId));
        assertEquals(UserType.class.getName() + " cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando UserType id for nulo")
    void deveLancarExcecaoUserTypeIdNulo() {
        UserType userType = mock(UserType.class);
        when(userType.getId()).thenReturn(null);

        ValidationException ex = assertThrows(ValidationException.class,
                () -> useCase.execute(userType, requesterId));
        assertEquals("userTypeId cannot be null", ex.getMessage());
    }

}
