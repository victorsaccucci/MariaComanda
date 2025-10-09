package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.RestaurantJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantGatewayImplTest {

    private RestaurantJpaRepository restaurantJpaRepository;
    private RestaurantEntityMapper restaurantEntityMapper;
    private RestaurantGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        restaurantJpaRepository = mock(RestaurantJpaRepository.class);
        restaurantEntityMapper = mock(RestaurantEntityMapper.class);
        gateway = new RestaurantGatewayImpl(restaurantJpaRepository, restaurantEntityMapper);
    }

    @Test
    @DisplayName("Deve salvar Restaurant corretamente")
    void testSaveRestaurant() {
        Restaurant restaurant = new Restaurant();
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(UUID.randomUUID());

        when(restaurantEntityMapper.toEntity(restaurant)).thenReturn(entity);
        when(restaurantJpaRepository.save(entity)).thenReturn(entity);
        when(restaurantEntityMapper.toDomain(entity)).thenReturn(restaurant);

        Restaurant result = gateway.save(restaurant);

        assertNotNull(result);
        assertEquals(restaurant, result);

        verify(restaurantJpaRepository).save(entity);
    }

    @Test
    @DisplayName("Deve buscar Restaurant por ID")
    void testFindById() {
        UUID id = UUID.randomUUID();
        RestaurantEntity entity = new RestaurantEntity();
        Restaurant restaurant = new Restaurant();

        when(restaurantJpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(restaurantEntityMapper.toDomain(entity)).thenReturn(restaurant);

        Optional<Restaurant> result = gateway.findById(id);

        assertTrue(result.isPresent());
        assertEquals(restaurant, result.get());
    }

    @Test
    @DisplayName("Deve retornar vazio se Restaurant não existir")
    void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(restaurantJpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Restaurant> result = gateway.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar lista de Restaurants")
    void testFindAll() {
        RestaurantEntity entity = new RestaurantEntity();
        Restaurant restaurant = new Restaurant();

        when(restaurantJpaRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(entity)));

        when(restaurantEntityMapper.toDomain(entity)).thenReturn(restaurant);

        List<Restaurant> result = gateway.findAll(0, 10);

        assertEquals(1, result.size());
        assertEquals(restaurant, result.get(0));
    }

    @Test
    @DisplayName("Deve deletar Restaurant por ID")
    void testDeleteById() {
        UUID id = UUID.randomUUID();

        gateway.deleteById(id);

        verify(restaurantJpaRepository).deleteById(id);
    }
}
