package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.*;
import com.fiap.mariacomanda.core.dto.restaurant.input.*;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;

import java.util.List;
import java.util.UUID;

public class RestaurantController {
    private final CreateRestaurantUseCase createUseCase;
    private final GetRestaurantUseCase getUseCase;
    private final ListRestaurantsUseCase listUseCase;
    private final UpdateRestaurantUseCase updateUseCase;
    private final DeleteRestaurantUseCase deleteUseCase;
    private final RestaurantMapper restaurantMapper;

    public RestaurantController(CreateRestaurantUseCase createUseCase, GetRestaurantUseCase getUseCase, ListRestaurantsUseCase listUseCase,
                                UpdateRestaurantUseCase updateUseCase, DeleteRestaurantUseCase deleteUseCase, RestaurantMapper restaurantMapper) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    public CreateRestaurantOutputDTO create(CreateRestaurantInputDTO inputDTO) {
        Restaurant restaurant = restaurantMapper.mapCreate(inputDTO);
        Restaurant created = createUseCase.execute(restaurant);
        return restaurantMapper.mapCreate(created);
    }

    public GetRestaurantOutputDTO get(GetRestaurantInputDTO inputDTO) {
        Restaurant restaurant = getUseCase.execute(UUID.fromString(inputDTO.name())).orElseThrow();
        return restaurantMapper.mapToGetOutputDTO(restaurant);
    }

    public List<GetRestaurantOutputDTO> list(ListRestaurantsInputDTO inputDTO) {
        List<Restaurant> restaurants = listUseCase.execute(inputDTO.page(), inputDTO.size());
        return restaurantMapper.mapToGetOutputDTOList(restaurants);
    }

    public GetRestaurantOutputDTO update(UpdateRestaurantInputDTO inputDTO) {
        Restaurant restaurant = restaurantMapper.mapUpdate(inputDTO);
        Restaurant updated = updateUseCase.execute(restaurant);
        return restaurantMapper.mapToGetOutputDTO(updated);
    }

    public void delete(DeleteRestaurantInputDTO inputDTO) {
        UUID id = inputDTO.id();
        deleteUseCase.execute(id);
    }
}
