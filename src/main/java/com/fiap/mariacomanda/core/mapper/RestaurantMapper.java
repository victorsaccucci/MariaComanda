package com.fiap.mariacomanda.core.mapper;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.dto.restaurant.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;

import java.util.List;

public interface RestaurantMapper {

    Restaurant mapCreate(CreateRestaurantInputDTO dto);

    Restaurant mapUpdate(UpdateRestaurantInputDTO dto);

    CreateRestaurantOutputDTO mapCreate(Restaurant restaurant);

    GetRestaurantOutputDTO mapToGetOutputDTO(Restaurant restaurant);

    List<GetRestaurantOutputDTO> mapToGetOutputDTOList(List<Restaurant> restaurants);

    CreateRestaurantInputDTO map(RestaurantJson json);

}
