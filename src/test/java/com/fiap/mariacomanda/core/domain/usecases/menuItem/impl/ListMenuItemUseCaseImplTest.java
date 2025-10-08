package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ListMenuItemUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private ListMenuItemUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        useCase = new ListMenuItemUseCaseImpl(menuItemGateway);
    }

    @Test
    @DisplayName("Deve listar MenuItems com sucesso")
    void execute_deveListarMenuItemsComSucesso() {
        UUID restaurantId = UUID.randomUUID();

        List<MenuItem> menuItems = List.of(
                new MenuItem(UUID.randomUUID(), restaurantId, "Pizza", "Deliciosa pizza", new BigDecimal("49.90"), false, "pizza.png"),
                new MenuItem(UUID.randomUUID(), restaurantId, "Lasanha", "Com queijo extra", new BigDecimal("39.90"), false, "lasanha.png")
        );

        when(menuItemGateway.findByRestaurant(restaurantId, 0, 10)).thenReturn(menuItems);

        List<MenuItem> result = useCase.execute(restaurantId, 0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        verify(menuItemGateway).findByRestaurant(restaurantId, 0, 10);
    }

}