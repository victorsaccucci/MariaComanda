package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.RestaurantController;
import com.fiap.mariacomanda.core.dto.restaurant.input.*;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller.RestaurantApi;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RestaurantApiController implements RestaurantApi {

    private final RestaurantController restaurantController;

    private final RestaurantJsonMapper restaurantJsonMapper;

    public CreateRestaurantOutputDTO create(@Valid @RequestBody RestaurantJson restaurantJson) {
        CreateRestaurantInputDTO inputDTO = restaurantJsonMapper.toCreateInput(restaurantJson);
        return restaurantController.create(inputDTO);
    }

    public GetRestaurantOutputDTO get(@PathVariable String name) {
        GetRestaurantInputDTO inputDTO = new GetRestaurantInputDTO(name);
        return restaurantController.get(inputDTO);
    }

    public List<GetRestaurantOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        ListRestaurantsInputDTO inputDTO = new ListRestaurantsInputDTO(page, size);
        return restaurantController.list(inputDTO);
    }

    public GetRestaurantOutputDTO update(@PathVariable UUID id, @RequestBody UpdateRestaurantInputDTO dto) {
        UpdateRestaurantInputDTO inputDTO = new UpdateRestaurantInputDTO(id, dto.name(), dto.address(), dto.cuisineType(), dto.openingHours(), dto.ownerUserId());
        return restaurantController.update(inputDTO);
    }

    public void delete(@PathVariable UUID id) {
        DeleteRestaurantInputDTO inputDTO = new DeleteRestaurantInputDTO(id);
        restaurantController.delete(inputDTO);
    }
}
