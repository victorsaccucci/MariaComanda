package com.fiap.mariacomanda.infrastructure.database.mapper;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapper {
    public Restaurant mapCreate(CreateRestaurantInputDTO dto) {
        return new Restaurant(
            dto.getId(), 
            dto.getName(), 
            dto.getAddress(), 
            dto.getCuisineType(), 
            dto.getOpeningHours(), 
            dto.getOwnerUserId());
    }

    public Restaurant mapUpdate(UpdateRestaurantInputDTO dto) {
        return new Restaurant(
            dto.getId(), 
            dto.getName(), 
            dto.getAddress(), 
            dto.getCuisineType(), 
            dto.getOpeningHours(), 
            dto.getOwnerUserId());
    }

    public CreateRestaurantOutputDTO mapCreate(Restaurant restaurant) {
        return new CreateRestaurantOutputDTO(
            restaurant.id(),
            restaurant.name(),
            restaurant.address(),
            restaurant.cuisineType(),
            restaurant.openingHours(),
            restaurant.ownerUserId()
        );
    }

    public GetRestaurantOutputDTO mapToGetOutputDTO(Restaurant restaurant) {
        return new GetRestaurantOutputDTO(
            restaurant.id(),
            restaurant.name(),
            restaurant.address(),
            restaurant.cuisineType(),
            restaurant.openingHours(),
            restaurant.ownerUserId()
        );
    }

    public List<GetRestaurantOutputDTO> mapToGetOutputDTOList(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::mapToGetOutputDTO).collect(Collectors.toList());
    }

    public CreateRestaurantInputDTO map(RestaurantJson json) {
        return new CreateRestaurantInputDTO(
            json.getId(),
            json.getName(),
            json.getAddress(),
            json.getCuisineType(),
            json.getOpeningHours(),
            json.getOwnerUserId()
        );
    }
}
