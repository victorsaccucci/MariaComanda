package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.RestaurantValidator;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.DeleteRestaurantUseCase;

import java.util.UUID;

public class DeleteRestaurantUseCaseImpl implements DeleteRestaurantUseCase {
    private final RestaurantGateway gateway;
    private final UserGateway userGateway;

    public DeleteRestaurantUseCaseImpl(RestaurantGateway gateway, UserGateway userGateway) {
        this.gateway = gateway;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UUID id, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(id, "restaurantId");

        RequesterValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        RequesterValidator.validateRequesterIsOwner(requester, "delete restaurants");

        Restaurant restaurant = gateway.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found for id: " + id));
        RestaurantValidator.validateUserOwnsRestaurant(restaurant, requesterUserId);

        gateway.deleteById(id);
    }
}
