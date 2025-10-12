package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.RestaurantValidator;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.UpdateRestaurantUseCase;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;

import java.util.UUID;

public class UpdateRestaurantUseCaseImpl implements UpdateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public UpdateRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    @Override
    public Restaurant execute(UpdateRestaurantInputDTO inputDTO, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(inputDTO, UpdateRestaurantInputDTO.class.getName());
        NullObjectValidator.validateNotNull(inputDTO.id(), "restaurantId");

        RequesterValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        RequesterValidator.validateRequesterIsOwner(requester, "update restaurants");

        Restaurant existing = restaurantGateway.findById(inputDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        RestaurantValidator.validateUserOwnsRestaurant(existing, requesterUserId);

        UUID ownerUserId = inputDTO.ownerUserId() != null ? inputDTO.ownerUserId() : existing.getOwnerUserId();
        NullObjectValidator.validateNotNull(ownerUserId, "ownerUserId");

        User owner = ownerUserId.equals(requesterUserId)
                ? requester
                : userGateway.findById(ownerUserId)
                        .orElseThrow(() -> new IllegalArgumentException("Owner user not found"));
        RequesterValidator.validateRequesterIsOwner(owner, "own restaurants");

        Restaurant merged = new Restaurant(
            existing.getId(),
            updateValue(inputDTO.name(), existing.getName()),
            updateValue(inputDTO.address(), existing.getAddress()),
            updateValue(inputDTO.cuisineType(), existing.getCuisineTypeForDisplay()),
            updateValue(inputDTO.openingHours(), existing.getOpeningHoursForStorage()),
            ownerUserId
        );

        return restaurantGateway.save(merged);
    }

    private String updateValue(String newValue, String current) {
        return newValue != null ? newValue : current;
    }
}