package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GetMenuItemUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private GetMenuItemUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        useCase = new GetMenuItemUseCaseImpl(menuItemGateway);
    }

    @Test
    @DisplayName("Deve retornar MenuItem quando encontrado")
    void execute_deveRetornarMenuItemQuandoEncontrado() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(id, UUID.randomUUID(), "Pizza", "Deliciosa pizza", new BigDecimal("49.90"), false, "foto.png");

        when(menuItemGateway.findById(id)).thenReturn(Optional.of(menuItem));

        Optional<MenuItem> result = useCase.execute(id);

        assertTrue(result.isPresent());
        assertEquals(menuItem, result.get());
        verify(menuItemGateway).findById(id);
    }

    @Test
    @DisplayName("Deve retornar Optional.empty() quando MenuItem não for encontrado")
    void execute_deveRetornarVazioQuandoMenuItemNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(menuItemGateway.findById(id)).thenReturn(Optional.empty());

        Optional<MenuItem> result = useCase.execute(id);

        assertTrue(result.isEmpty());
        verify(menuItemGateway).findById(id);
    }

}
