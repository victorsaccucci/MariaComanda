package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.gateway.RestaurantGateway;

import java.util.Optional;
import java.util.UUID;

public class GetRestaurantUseCaseImpl implements GetRestaurantUseCase {
    private final RestaurantGateway gateway;

    public GetRestaurantUseCaseImpl(RestaurantGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Optional<Restaurant> execute(UUID id) {
        return gateway.findById(id);
    }
}
