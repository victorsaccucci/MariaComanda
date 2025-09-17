package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.gateway.RestaurantGateway;

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