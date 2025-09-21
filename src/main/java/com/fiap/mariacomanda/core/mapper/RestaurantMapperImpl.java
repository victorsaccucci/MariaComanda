package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant mapCreate(CreateRestaurantInputDTO dto) {
        return new Restaurant(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getCuisineType(),
                dto.getOpeningHours(),
                dto.getOwnerUserId());
    }

    @Override
    public Restaurant mapUpdate(UpdateRestaurantInputDTO dto) {
        return new Restaurant(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getCuisineType(),
                dto.getOpeningHours(),
                dto.getOwnerUserId());
    }

    @Override
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

    @Override
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

    @Override
    public List<GetRestaurantOutputDTO> mapToGetOutputDTOList(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::mapToGetOutputDTO).collect(Collectors.toList());
    }

    @Override
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
