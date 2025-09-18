package com.fiap.mariacomanda.core.controller;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.*;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;
import com.fiap.mariacomanda.core.usecases.restaurant.*;
import java.util.List;

public class RestaurantController {
    private final CreateRestaurantUseCase create;
    private final GetRestaurantUseCase get;
    private final ListRestaurantsUseCase list;
    private final UpdateRestaurantUseCase update;
    private final DeleteRestaurantUseCase delete;
    private final RestaurantMapper restaurantMapper;

    public RestaurantController(CreateRestaurantUseCase create, GetRestaurantUseCase get, ListRestaurantsUseCase list,
                                UpdateRestaurantUseCase update, DeleteRestaurantUseCase delete, RestaurantMapper restaurantMapper) {
        this.create = create;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
        this.restaurantMapper = restaurantMapper;
    }

    public CreateRestaurantOutputDTO create(CreateRestaurantInputDTO inputDTO) {
        Restaurant restaurant = restaurantMapper.mapCreate(inputDTO);
        Restaurant created = create.execute(restaurant);
        return restaurantMapper.mapCreate(created);
    }

    public GetRestaurantOutputDTO get(GetRestaurantInputDTO inputDTO) {
        Restaurant restaurant = get.execute(inputDTO.getId())
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return restaurantMapper.mapToGetOutputDTO(restaurant);
    }

    public List<GetRestaurantOutputDTO> list(ListRestaurantsInputDTO inputDTO) {
        List<Restaurant> restaurants = list.execute(inputDTO.getPage(), inputDTO.getSize());
        return restaurantMapper.mapToGetOutputDTOList(restaurants);
    }

    public GetRestaurantOutputDTO update(UpdateRestaurantInputDTO inputDTO) {
        Restaurant restaurant = restaurantMapper.mapUpdate(inputDTO);
        Restaurant updated = update.execute(restaurant);
        return restaurantMapper.mapToGetOutputDTO(updated);
    }

    public void delete(DeleteRestaurantInputDTO inputDTO) {
        delete.execute(inputDTO.getId());
    }
}
