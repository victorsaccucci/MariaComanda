package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.application.usecases.restaurant.*;
import com.fiap.mariacomanda.domain.entity.Restaurant;
import com.fiap.mariacomanda.infrastructure.web.dto.RestaurantDTO;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final CreateRestaurantUseCase create;
    private final GetRestaurantUseCase get;
    private final ListRestaurantsUseCase list;
    private final UpdateRestaurantUseCase update;
    private final DeleteRestaurantUseCase delete;

    public RestaurantController(CreateRestaurantUseCase create, GetRestaurantUseCase get, ListRestaurantsUseCase list,
                                UpdateRestaurantUseCase update, DeleteRestaurantUseCase delete){
        this.create = create; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }

    @PostMapping
    public RestaurantDTO create(@RequestBody RestaurantDTO dto){
        var saved = create.execute(new Restaurant(
                null, dto.name(), dto.address(), dto.cuisineType(), dto.openingHours(), dto.ownerUserId()
        ));
        return toDto(saved);
    }

    @GetMapping("/{id}")
    public RestaurantDTO get(@PathVariable UUID id){
        var r = get.execute(id).orElseThrow(() -> new NoSuchElementException("Restaurant not found"));
        return toDto(r);
    }

    @GetMapping
    public List<RestaurantDTO> list(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size){
        return list.execute(page, size).stream().map(this::toDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public RestaurantDTO update(@PathVariable UUID id, @RequestBody RestaurantDTO dto){
        var updated = update.execute(new Restaurant(
                id, dto.name(), dto.address(), dto.cuisineType(), dto.openingHours(), dto.ownerUserId()
        ));
        return toDto(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){ delete.execute(id); }

    private RestaurantDTO toDto(Restaurant r){
        return new RestaurantDTO(r.id(), r.name(), r.address(), r.cuisineType(), r.openingHours(), r.ownerUserId());
    }
}
