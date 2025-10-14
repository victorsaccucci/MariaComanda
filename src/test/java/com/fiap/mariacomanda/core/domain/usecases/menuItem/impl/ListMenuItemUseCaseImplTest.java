package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListMenuItemUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private RestaurantGateway restaurantGateway;
    private ListMenuItemUseCaseImpl useCase;

    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        useCase = new ListMenuItemUseCaseImpl(menuItemGateway, restaurantGateway);
        restaurantId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve listar itens de menu quando restaurante existir")
    void deveListarItensDeMenuQuandoRestauranteExistir() {
        MenuItem item1 = new MenuItem(UUID.randomUUID(), restaurantId, "Pizza", "Deliciosa", new BigDecimal("45.0"), true, "/images/pizza.jpg");
        MenuItem item2 = new MenuItem(UUID.randomUUID(), restaurantId, "Burger", "Hambúrguer artesanal", new BigDecimal("30.0"), false, "/images/burger.jpg");

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(mock(com.fiap.mariacomanda.core.domain.entity.Restaurant.class)));
        when(menuItemGateway.findByRestaurant(restaurantId, 0, 10)).thenReturn(List.of(item1, item2));

        List<MenuItem> result = useCase.execute(restaurantId, 0, 10);

        assertEquals(2, result.size());
        verify(menuItemGateway).findByRestaurant(restaurantId, 0, 10);
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não existir")
    void deveLancarExcecaoQuandoRestauranteNaoExistir() {
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(restaurantId, 0, 10));

        assertTrue(exception.getMessage().contains("Restaurant"));
    }

    @Test
    @DisplayName("Deve listar itens de menu sem filtro de restaurante")
    void deveListarItensDeMenuSemFiltroDeRestaurante() {
        MenuItem item1 = new MenuItem(UUID.randomUUID(), UUID.randomUUID(), "Sushi", "Sushi fresco", new BigDecimal("50.0"), false, "/images/sushi.jpg");
        MenuItem item2 = new MenuItem(UUID.randomUUID(), UUID.randomUUID(), "Salada", "Salada verde", new BigDecimal("20.0"), true, "/images/salada.jpg");

        when(menuItemGateway.findByRestaurant(null, 0, 10)).thenReturn(List.of(item1, item2));

        List<MenuItem> result = useCase.execute(null, 0, 10);

        assertEquals(2, result.size());
        verify(menuItemGateway).findByRestaurant(null, 0, 10);
    }
}
