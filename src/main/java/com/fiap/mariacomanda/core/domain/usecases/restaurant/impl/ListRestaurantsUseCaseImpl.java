package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.ListRestaurantsUseCase;

import java.util.List;

public class ListRestaurantsUseCaseImpl implements ListRestaurantsUseCase {
    private final RestaurantGateway gateway;

    public ListRestaurantsUseCaseImpl(RestaurantGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Restaurant> execute(int page, int size) {
        return gateway.findAll(page, size);
    }
}