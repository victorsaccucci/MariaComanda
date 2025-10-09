package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.RestaurantController;
import com.fiap.mariacomanda.core.dto.restaurant.input.*;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.infrastructure.config.swagger.openapi.controller.RestaurantApi;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateRestaurantJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateRestaurantJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RestaurantApiController implements RestaurantApi {

    private final RestaurantController restaurantController;

    private final RestaurantJsonMapper restaurantJsonMapper;

    public CreateRestaurantOutputDTO create(@RequestHeader("X-Requester-User-Id") UUID requesterUserId, @Valid @RequestBody CreateRestaurantJson restaurantJson) {
        CreateRestaurantInputDTO inputDTO = restaurantJsonMapper.toCreateInput(restaurantJson);
        return restaurantController.create(inputDTO, requesterUserId);
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

    public GetRestaurantOutputDTO update(@RequestHeader("X-Requester-User-Id") UUID requesterUserId,
                                        @PathVariable UUID id,
                                        @Valid @RequestBody UpdateRestaurantJson updateRestaurantJson) {
        UpdateRestaurantInputDTO inputDTO = restaurantJsonMapper.toUpdateInput(id, updateRestaurantJson);
        return restaurantController.update(inputDTO, requesterUserId);
    }

    public void delete(@PathVariable UUID id) {
        DeleteRestaurantInputDTO inputDTO = new DeleteRestaurantInputDTO(id);
        restaurantController.delete(inputDTO);
    }
}
