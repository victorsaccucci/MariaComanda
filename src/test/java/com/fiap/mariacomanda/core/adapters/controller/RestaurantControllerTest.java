package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.*;
import com.fiap.mariacomanda.core.dto.restaurant.input.*;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    private CreateRestaurantUseCase createUseCase;
    private GetRestaurantUseCase getUseCase;
    private ListRestaurantsUseCase listUseCase;
    private UpdateRestaurantUseCase updateUseCase;
    private DeleteRestaurantUseCase deleteUseCase;
    private RestaurantMapper restaurantMapper;
    private RestaurantController controller;

    private UUID restaurantId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        createUseCase = mock(CreateRestaurantUseCase.class);
        getUseCase = mock(GetRestaurantUseCase.class);
        listUseCase = mock(ListRestaurantsUseCase.class);
        updateUseCase = mock(UpdateRestaurantUseCase.class);
        deleteUseCase = mock(DeleteRestaurantUseCase.class);
        restaurantMapper = mock(RestaurantMapper.class);

        controller = new RestaurantController(
                createUseCase, getUseCase, listUseCase, updateUseCase, deleteUseCase, restaurantMapper
        );
        restaurantId = UUID.randomUUID();
        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar restaurante com sucesso")
    void testCreateRestaurant() {
        CreateRestaurantInputDTO inputDTO = mock(CreateRestaurantInputDTO.class);
        Restaurant restaurant = mock(Restaurant.class);
        CreateRestaurantOutputDTO outputDTO = mock(CreateRestaurantOutputDTO.class);

        when(createUseCase.execute(inputDTO, userId)).thenReturn(restaurant);
        when(restaurantMapper.toCreateOutput(restaurant)).thenReturn(outputDTO);

        CreateRestaurantOutputDTO result = controller.create(inputDTO, userId);

        assertEquals(outputDTO, result);
        verify(createUseCase).execute(inputDTO, userId);
        verify(restaurantMapper).toCreateOutput(restaurant);
    }

    @Test
    @DisplayName("Deve buscar restaurante por id (nome) e retornar DTO")
    void testGetRestaurant() {
        String idString = restaurantId.toString();
        GetRestaurantInputDTO inputDTO = mock(GetRestaurantInputDTO.class);
        Restaurant restaurant = mock(Restaurant.class);
        GetRestaurantOutputDTO outputDTO = mock(GetRestaurantOutputDTO.class);

        when(inputDTO.name()).thenReturn(idString);
        when(getUseCase.execute(restaurantId)).thenReturn(restaurant);
        when(restaurantMapper.toGetOutput(restaurant)).thenReturn(outputDTO);

        GetRestaurantOutputDTO result = controller.get(inputDTO);

        assertEquals(outputDTO, result);
        verify(getUseCase).execute(restaurantId);
        verify(restaurantMapper).toGetOutput(restaurant);
    }

    @Test
    @DisplayName("Deve listar restaurantes com sucesso")
    void testListRestaurants() {
        ListRestaurantsInputDTO inputDTO = mock(ListRestaurantsInputDTO.class);
        List<Restaurant> restaurants = List.of(mock(Restaurant.class), mock(Restaurant.class));
        List<GetRestaurantOutputDTO> expectedList = List.of(mock(GetRestaurantOutputDTO.class), mock(GetRestaurantOutputDTO.class));

        when(inputDTO.page()).thenReturn(0);
        when(inputDTO.size()).thenReturn(5);

        when(listUseCase.execute(0, 5)).thenReturn(restaurants);
        when(restaurantMapper.toGetOutputList(restaurants)).thenReturn(expectedList);

        List<GetRestaurantOutputDTO> result = controller.list(inputDTO);

        assertEquals(expectedList, result);
        verify(listUseCase).execute(0, 5);
        verify(restaurantMapper).toGetOutputList(restaurants);
    }

    @Test
    @DisplayName("Deve atualizar restaurante com sucesso")
    void testUpdateRestaurant() {
        UpdateRestaurantInputDTO inputDTO = mock(UpdateRestaurantInputDTO.class);
        Restaurant updatedRestaurant = mock(Restaurant.class);
        GetRestaurantOutputDTO outputDTO = mock(GetRestaurantOutputDTO.class);

        when(updateUseCase.execute(inputDTO, userId)).thenReturn(updatedRestaurant);
        when(restaurantMapper.toGetOutput(updatedRestaurant)).thenReturn(outputDTO);

        GetRestaurantOutputDTO result = controller.update(inputDTO, userId);

        assertEquals(outputDTO, result);
        verify(updateUseCase).execute(inputDTO, userId);
        verify(restaurantMapper).toGetOutput(updatedRestaurant);
    }

    @Test
    @DisplayName("Deve deletar restaurante com sucesso")
    void testDeleteRestaurant() {
        DeleteRestaurantInputDTO inputDTO = mock(DeleteRestaurantInputDTO.class);
        when(inputDTO.id()).thenReturn(restaurantId);

        controller.delete(inputDTO, userId);

        verify(deleteUseCase).execute(restaurantId, userId);
    }
}