package com.fiap.mariacomanda.core.domain.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.gateway.RestaurantGateway;

import java.util.Optional;
import java.util.UUID;

public class GetRestaurantUseCaseImpl implements GetRestaurantUseCase {
    private final RestaurantGateway repository;

    public GetRestaurantUseCaseImpl(RestaurantGateway repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Restaurant> execute(UUID id) {
        return repository.findById(id);
    }
}
