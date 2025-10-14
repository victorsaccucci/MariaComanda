package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserTypeUseCaseImplTest {

    private UserTypeGateway gateway;
    private UserGateway userGateway;
    private DeleteUserTypeUseCaseImpl useCase;

    private UUID userTypeId;
    private UUID requesterId;

    @BeforeEach
    void setUp() {
        gateway = mock(UserTypeGateway.class);
        userGateway = mock(UserGateway.class);
        useCase = new DeleteUserTypeUseCaseImpl(gateway, userGateway);

        userTypeId = UUID.randomUUID();
        requesterId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve deletar UserType com sucesso")
    void deveDeletarUserTypeComSucesso() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        UserType userType = new UserType(userTypeId, "ADMIN", UserType.OWNER);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(gateway.findById(userTypeId)).thenReturn(Optional.of(userType));

        assertDoesNotThrow(() -> useCase.execute(userTypeId, requesterId));
        verify(gateway).deleteById(userTypeId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for encontrado")
    void deveLancarExcecaoRequesterNaoEncontrado() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(userTypeId, requesterId));
        assertEquals("Requester user not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando UserType não for encontrado")
    void deveLancarExcecaoUserTypeNaoEncontrado() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER));
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(gateway.findById(userTypeId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(userTypeId, requesterId));
        assertEquals("UserType not found for id: " + userTypeId, ex.getMessage());
    }
}
