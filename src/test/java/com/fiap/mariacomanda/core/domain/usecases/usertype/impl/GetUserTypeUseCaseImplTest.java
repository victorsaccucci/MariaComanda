package com.fiap.mariacomanda.core.domain.usecases.usertype.impl;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GetUserTypeUseCaseImplTest {

    private UserTypeGateway gateway;
    private GetUserTypeUseCaseImpl useCase;

    private UUID userTypeId;

    @BeforeEach
    void setUp() {
        gateway = mock(UserTypeGateway.class);
        useCase = new GetUserTypeUseCaseImpl(gateway);

        userTypeId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve retornar UserType quando existir")
    void deveRetornarUserTypeQuandoExistir() {
        UserType userType = new UserType(userTypeId, "ADMIN", UserType.CUSTOMER);
        when(gateway.findById(userTypeId)).thenReturn(Optional.of(userType));

        Optional<UserType> result = useCase.execute(userTypeId);

        assertTrue(result.isPresent());
        assertEquals(userType, result.get());
        verify(gateway).findById(userTypeId);
    }

}
