package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class DeleteRestaurantUseCaseImplTest {

    private RestaurantGateway restaurantGateway;
    private DeleteRestaurantUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        useCase = new DeleteRestaurantUseCaseImpl(restaurantGateway);
    }

    @Test
    @DisplayName("Deve deletar restaurante pelo ID")
    void deveDeletarRestaurantePeloId() {
        UUID id = UUID.randomUUID();

        useCase.execute(id);

        verify(restaurantGateway, times(1)).deleteById(id);
    }
}