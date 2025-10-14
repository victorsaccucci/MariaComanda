package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteUserUseCaseImplTest {

    private UserGateway userGateway;
    private DeleteUserUseCaseImpl useCase;

    private UUID requesterId;
    private UUID targetUserId;
    private UserType ownerType;
    private User requester;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        useCase = new DeleteUserUseCaseImpl(userGateway);

        requesterId = UUID.randomUUID();
        targetUserId = UUID.randomUUID();
        ownerType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        requester = new User(requesterId, "Owner", "owner@test.com", "senha", ownerType);
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuarioComSucesso() {
        UserType type = new UserType(UUID.randomUUID(), "CUSTOMER", UserType.CUSTOMER);
        User targetUser = new User(targetUserId, "John Doe", "john@test.com", "senha", type);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(targetUserId)).thenReturn(Optional.of(targetUser));

        useCase.execute(targetUserId, requesterId);

        verify(userGateway).deleteById(targetUserId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando id do usuário for nulo")
    void deveLancarExcecaoQuandoUserIdNulo() {
        ValidationException ex = assertThrows(ValidationException.class,
                () -> useCase.execute(null, requesterId));
        assertEquals("userId cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for encontrado")
    void deveLancarExcecaoQuandoRequesterNaoEncontrado() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(targetUserId, requesterId));
        assertEquals("Requester user not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário alvo não for encontrado")
    void deveLancarExcecaoQuandoUsuarioAlvoNaoEncontrado() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(targetUserId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(targetUserId, requesterId));
        assertEquals("User not found for id: " + targetUserId, ex.getMessage());
    }
}
