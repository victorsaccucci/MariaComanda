package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantDtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantDtoMapperImpl implements RestaurantDtoMapper {

    @Override
    public Restaurant toDomain(CreateRestaurantInputDTO dto) {
        return new Restaurant(
                dto.id(),
                dto.name(),
                dto.address(),
                dto.cuisineType(),
                dto.openingHours(),
                dto.ownerUserId());
    }

    @Override
    public Restaurant toDomain(UpdateRestaurantInputDTO dto) {
        return new Restaurant(
                dto.id(),
                dto.name(),
                dto.address(),
                dto.cuisineType(),
                dto.openingHours(),
                dto.ownerUserId());
    }

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
