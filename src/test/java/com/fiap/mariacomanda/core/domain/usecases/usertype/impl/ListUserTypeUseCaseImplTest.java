package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ListUserTypeUseCaseImplTest {

    private UserTypeGateway gateway;
    private ListUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        gateway = mock(UserTypeGateway.class);
        useCase = new ListUserTypeUseCaseImpl(gateway);
    }

    @Test
    @DisplayName("Deve retornar lista de UserTypes quando existirem")
    void deveRetornarListaDeUserTypes() {
        UserType type1 = new UserType(UUID.randomUUID(), "ADMIN", UserType.OWNER);
        UserType type2 = new UserType(UUID.randomUUID(), "USER", UserType.OWNER);
        List<UserType> mockList = Arrays.asList(type1, type2);

        when(gateway.findAll(0, 10)).thenReturn(mockList);

        List<UserType> result = useCase.execute(0, 10);

        assertEquals(2, result.size());
        assertEquals(type1, result.get(0));
        assertEquals(type2, result.get(1));
        verify(gateway).findAll(0, 10);
    }

}
