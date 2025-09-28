package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant mapCreate(CreateRestaurantInputDTO dto) {
        return new Restaurant(
                dto.id(),
                dto.name(),
                dto.address(),
                dto.cuisineType(),
                dto.openingHours(),
                dto.ownerUserId()
        );
    }

    @Override
    public Restaurant mapUpdate(UpdateRestaurantInputDTO dto) {
        return new Restaurant(
                dto.id(),
                dto.name(),
                dto.address(),
                dto.cuisineType(),
                dto.openingHours(),
                dto.ownerUserId()
        );
    }

    @Override
    public CreateRestaurantOutputDTO mapCreate(Restaurant restaurant) {
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
    public GetRestaurantOutputDTO mapToGetOutputDTO(Restaurant restaurant) {
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
    public List<GetRestaurantOutputDTO> mapToGetOutputDTOList(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::mapToGetOutputDTO).collect(Collectors.toList());
    }

}
