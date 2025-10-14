package com.fiap.mariacomanda.core.domain.usecases.user.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListUserUseCaseImplTest {

    private UserGateway userGateway;
    private ListUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        useCase = new ListUserUseCaseImpl(userGateway);
    }

    @Test
    @DisplayName("Deve retornar lista de usuários preenchida")
    void deveRetornarListaUsuariosPreenchida() {
        UserType type = new UserType(UUID.randomUUID(), "CUSTOMER", UserType.CUSTOMER);
        User user1 = new User(UUID.randomUUID(), "John Doe", "john@test.com", "senha", type);
        User user2 = new User(UUID.randomUUID(), "Jane Doe", "jane@test.com", "senha", type);

        List<User> mockList = List.of(user1, user2);

        when(userGateway.findAll(0, 10)).thenReturn(mockList);

        List<User> result = useCase.execute(0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
        verify(userGateway).findAll(0, 10);
    }
}
