package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListRestaurantsUseCaseImplTest {

    private RestaurantGateway restaurantGateway;
    private ListRestaurantsUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        useCase = new ListRestaurantsUseCaseImpl(restaurantGateway);
    }

    @Test
    @DisplayName("Deve listar restaurantes conforme página e tamanho")
    void deveListarRestaurantesPorPaginacao() {
        int page = 1;
        int size = 2;
        Restaurant r1 = mock(Restaurant.class);
        Restaurant r2 = mock(Restaurant.class);
        List<Restaurant> lista = Arrays.asList(r1, r2);

        when(restaurantGateway.findAll(page, size)).thenReturn(lista);

        List<Restaurant> resultado = useCase.execute(page, size);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(r1));
        assertTrue(resultado.contains(r2));
        verify(restaurantGateway, times(1)).findAll(page, size);
    }

    @Test
    @DisplayName("Deve retornar lista vazia se não houver restaurantes")
    void deveRetornarListaVaziaQuandoNaoHouverRestaurantes() {
        int page = 0;
        int size = 5;

        when(restaurantGateway.findAll(page, size)).thenReturn(List.of());

        List<Restaurant> resultado = useCase.execute(page, size);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(restaurantGateway, times(1)).findAll(page, size);
    }
}