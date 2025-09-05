package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.port.RestaurantRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {
    private final RestaurantRepositoryPort repository;

    @Override
    public Restaurant execute(Restaurant newRestaurant) {
        return repository.save(newRestaurant);
    }
}