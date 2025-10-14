package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetUserUseCaseImplTest {

    private UserGateway userGateway;
    private GetUserUseCaseImpl useCase;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        useCase = new GetUserUseCaseImpl(userGateway);

        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve retornar usuário quando encontrado")
    void deveRetornarUsuarioQuandoEncontrado() {
        UserType type = new UserType(UUID.randomUUID(), "CUSTOMER", UserType.CUSTOMER);
        User user = new User(userId, "John Doe", "john@test.com", "senha", type);

        when(userGateway.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = useCase.execute(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userGateway).findById(userId);
    }
}
