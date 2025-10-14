package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetRestaurantUseCaseImplTest {

    private RestaurantGateway restaurantGateway;
    private GetRestaurantUseCaseImpl useCase;

    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        useCase = new GetRestaurantUseCaseImpl(restaurantGateway);
        restaurantId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve retornar restaurante existente pelo ID")
    void deveRetornarRestauranteExistente() {
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante Teste", "Rua A, 123", "Italiana", "10:00-22:00", UUID.randomUUID());
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        Restaurant result = useCase.execute(restaurantId);

        assertNotNull(result);
        assertEquals(restaurantId, result.getId());
        assertEquals("Restaurante Teste", result.getName());
        verify(restaurantGateway, times(1)).findById(restaurantId);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando restaurante não existir")
    void deveLancarExcecaoQuandoRestauranteNaoExistir() {
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> useCase.execute(restaurantId));
        assertEquals("Restaurant not found with identifier: " + restaurantId, exception.getMessage());
        verify(restaurantGateway, times(1)).findById(restaurantId);
    }
}
