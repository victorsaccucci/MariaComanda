package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.GetRestaurantUseCase;

import java.util.UUID;

public class GetRestaurantUseCaseImpl implements GetRestaurantUseCase {
    private final RestaurantGateway gateway;

    public GetRestaurantUseCaseImpl(RestaurantGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Restaurant execute(UUID id) {
        return gateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant", id.toString()));
    }
}
