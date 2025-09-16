package com.fiap.mariacomanda.core.usecases.restaurant;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.gateway.RestaurantGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {
    private final RestaurantGateway gateway;

    public CreateRestaurantUseCaseImpl(RestaurantGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Restaurant execute(Restaurant newRestaurant) {
        return gateway.save(newRestaurant);
    }
}