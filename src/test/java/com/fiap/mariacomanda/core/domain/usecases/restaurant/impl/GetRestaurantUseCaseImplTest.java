package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
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

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        useCase = new GetRestaurantUseCaseImpl(restaurantGateway);
    }

    @Test
    @DisplayName("Deve retornar restaurante existente pelo ID")
    void deveRetornarRestauranteExistentePeloId() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantGateway.findById(id)).thenReturn(Optional.of(restaurant));

        Optional<Restaurant> result = useCase.execute(id);

        assertTrue(result.isPresent());
        assertEquals(restaurant, result.get());
        verify(restaurantGateway, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar Optional.empty() se restaurante não existir")
    void deveRetornarOptionalVazioSeRestauranteNaoExistir() {
        UUID id = UUID.randomUUID();

        when(restaurantGateway.findById(id)).thenReturn(Optional.empty());

        Optional<Restaurant> result = useCase.execute(id);

        assertFalse(result.isPresent());
        verify(restaurantGateway, times(1)).findById(id);
    }
}