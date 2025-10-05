package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantMapperImpl implements RestaurantMapper {

    // Entradas
    @Override
    public Restaurant toDomain(CreateRestaurantInputDTO dto) {
        return new Restaurant(
                dto.name(),
                dto.address(),
                dto.cuisineType(),
                dto.openingHours(),
                dto.ownerUserId()
        );
    }

    @Override
    public Restaurant toDomain(UpdateRestaurantInputDTO dto) {
        return new Restaurant(
                dto.id(),
                dto.name(),
                dto.address(),
                dto.cuisineType(),
                dto.openingHours(),
                dto.ownerUserId()
        );
    }

    // Saídas
    @Override
    public CreateRestaurantOutputDTO toCreateOutput(Restaurant restaurant) {
        return new CreateRestaurantOutputDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                restaurant.getOwnerUserId()
        );
    }

    @Override
    public GetRestaurantOutputDTO toGetOutput(Restaurant restaurant) {
        return new GetRestaurantOutputDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                restaurant.getOwnerUserId()
        );
    }

    @Override
    public List<GetRestaurantOutputDTO> toGetOutputList(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::toGetOutput).collect(Collectors.toList());
    }
}
