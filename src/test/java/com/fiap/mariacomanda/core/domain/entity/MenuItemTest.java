package com.fiap.mariacomanda.core.domain.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    private final UUID restaurantId = UUID.randomUUID();
    private final UUID menuItemId = UUID.randomUUID();

    @Test
    void deveCriarMenuItemValido() {
        MenuItem item = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa",
                new BigDecimal("45.00"), true, "/images/pizza.jpg");
        assertEquals("Pizza", item.getName());
        assertEquals(new BigDecimal("45.00"), item.getPrice());
        assertTrue(item.isDineInOnly());
    }

    @Test
    void deveLancarExcecaoSeRestaurantIdNulo() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new MenuItem(null, "Pizza", "Desc", new BigDecimal("10"), true, null));
        assertEquals("Restaurant ID cannot be null", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNomeNuloOuVazio() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, () ->
                new MenuItem(restaurantId, null, "Desc", new BigDecimal("10"), true, null));
        assertEquals("MenuItem name cannot be null or empty", ex1.getMessage());

        Exception ex2 = assertThrows(IllegalArgumentException.class, () ->
                new MenuItem(restaurantId, "   ", "Desc", new BigDecimal("10"), true, null));
        assertEquals("MenuItem name cannot be null or empty", ex2.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNomeMaiorQue120Caracteres() {
        String longName = "A".repeat(121);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new MenuItem(restaurantId, longName, "Desc", new BigDecimal("10"), true, null));
        assertEquals("MenuItem name cannot exceed 120 characters", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSePrecoNuloOuMenorIgualZero() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, () ->
                new MenuItem(restaurantId, "Pizza", "Desc", null, true, null));
        assertEquals("Price cannot be null", ex1.getMessage());

        Exception ex2 = assertThrows(IllegalArgumentException.class, () ->
                new MenuItem(restaurantId, "Pizza", "Desc", BigDecimal.ZERO, true, null));
        assertEquals("Price must be greater than zero", ex2.getMessage());
    }

    @Test
    void deveValidarSetters() {
        UUID restaurantId = UUID.randomUUID();
        UUID menuItemId = UUID.randomUUID();
        MenuItem item = new MenuItem(menuItemId, restaurantId, "Pizza", "Desc", new BigDecimal("10"), true, null);

        // restaurantId válido
        UUID newRestaurantId = UUID.randomUUID();
        item.setRestaurantId(newRestaurantId);
        assertEquals(newRestaurantId, item.getRestaurantId());

        // restaurantId nulo
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> item.setRestaurantId(null));
        assertEquals("Restaurant ID cannot be null", ex1.getMessage());

        // name válido
        item.setName("Burger");
        assertEquals("Burger", item.getName());

        // name nulo ou vazio
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> item.setName(null));
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> item.setName("   "));
        assertEquals("MenuItem name cannot be null or empty", ex2.getMessage());
        assertEquals("MenuItem name cannot be null or empty", ex3.getMessage());

        // price válido
        item.setPrice(new BigDecimal("20"));
        assertEquals(new BigDecimal("20"), item.getPrice());

        // price nulo ou <= 0
        Exception ex4 = assertThrows(IllegalArgumentException.class, () -> item.setPrice(null));
        Exception ex5 = assertThrows(IllegalArgumentException.class, () -> item.setPrice(BigDecimal.ZERO));
        assertEquals("Price cannot be null", ex4.getMessage());
        assertEquals("Price must be greater than zero", ex5.getMessage());
    }
}
