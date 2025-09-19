package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.controller.RestaurantController;
import com.fiap.mariacomanda.core.dto.restaurant.*;
import com.fiap.mariacomanda.core.dto.restaurant.DeleteRestaurantInputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapperImpl;
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

    private final RestaurantMapperImpl restaurantMapper;

    @PostMapping
    public CreateRestaurantOutputDTO create(@Valid @RequestBody RestaurantJson restaurantJson) {
        var inputDTO = restaurantMapper.map(restaurantJson);
        return restaurantController.create(inputDTO);
    }

    @GetMapping("/{id}")
    public GetRestaurantOutputDTO get(@PathVariable UUID id) {
        var inputDTO = new GetRestaurantInputDTO(id, null, null, null, null, null);
        return restaurantController.get(inputDTO);
    }

    @GetMapping
    public List<GetRestaurantOutputDTO> list(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size) {
        var inputDTO = new ListRestaurantsInputDTO(page, size);
        return restaurantController.list(inputDTO);
    }

    @PutMapping("/{id}")
    public GetRestaurantOutputDTO update(@PathVariable UUID id, @RequestBody UpdateRestaurantInputDTO dto) {
        var inputDTO = new UpdateRestaurantInputDTO(id, dto.getName(), dto.getAddress(), dto.getCuisineType(), dto.getOpeningHours(), dto.getOwnerUserId());
        return restaurantController.update(inputDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        var inputDTO = new DeleteRestaurantInputDTO(id);
        restaurantController.delete(inputDTO);
    }
}
