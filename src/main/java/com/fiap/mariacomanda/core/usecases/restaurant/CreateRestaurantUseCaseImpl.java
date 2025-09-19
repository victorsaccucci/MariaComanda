package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.gateway.RestaurantGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {
    private final RestaurantGateway repository;

    public CreateRestaurantUseCaseImpl(RestaurantGateway repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant execute(Restaurant newRestaurant) {
        return repository.save(newRestaurant);
    }
}