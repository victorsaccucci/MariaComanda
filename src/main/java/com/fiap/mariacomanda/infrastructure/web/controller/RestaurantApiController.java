package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.RestaurantController;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.DeleteRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.GetRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.ListRestaurantsInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.CreateRestaurantOutputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.output.GetRestaurantOutputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.RestaurantJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.RestaurantJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantApiController {

    private final RestaurantController restaurantController;

    private final RestaurantJsonMapper restaurantJsonMapper;

    @PostMapping
    public CreateRestaurantOutputDTO create(@Valid @RequestBody RestaurantJson restaurantJson) {
        CreateRestaurantInputDTO inputDTO = restaurantJsonMapper.map(restaurantJson);
        return restaurantController.create(inputDTO);
    }

    @GetMapping("/{name}")
    public GetRestaurantOutputDTO get(@PathVariable String name) {
        GetRestaurantInputDTO inputDTO = new GetRestaurantInputDTO(name);
        return restaurantController.get(inputDTO);
    }

    @GetMapping
    public List<GetRestaurantOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size) {
        ListRestaurantsInputDTO inputDTO = new ListRestaurantsInputDTO(page, size);
        return restaurantController.list(inputDTO);
    }

    @PutMapping("/{id}")
    public GetRestaurantOutputDTO update(@PathVariable UUID id, @RequestBody UpdateRestaurantInputDTO dto) {
        UpdateRestaurantInputDTO inputDTO = new UpdateRestaurantInputDTO(id, dto.name(), dto.address(), dto.cuisineType(), dto.openingHours(), dto.ownerUserId());
        return restaurantController.update(inputDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        DeleteRestaurantInputDTO inputDTO = new DeleteRestaurantInputDTO(id);
        restaurantController.delete(inputDTO);
    }
}
