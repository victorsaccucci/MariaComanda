package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import java.util.UUID;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.CreateRestaurantUseCase;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;

public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;
    private final RestaurantMapper restaurantMapper;
    private final UserGateway userGateway;

    public CreateRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, UserGateway userGateway,
                                    RestaurantMapper restaurantMapper) {
        this.restaurantGateway = restaurantGateway;
        this.restaurantMapper = restaurantMapper;
        this.userGateway = userGateway;
    }

    @Override
    public Restaurant execute(CreateRestaurantInputDTO inputDTO, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(inputDTO, CreateRestaurantInputDTO.class.getName());

        RequesterValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
        RequesterValidator.validateRequesterIsOwner(requester, "create restaurants");

        NullObjectValidator.validateNotNull(inputDTO.ownerUserId(), "Owner user ID");
        User ownerUser = resolveOwnerUser(inputDTO.ownerUserId(), requester);
        RequesterValidator.validateRequesterIsOwner(ownerUser, "be associated as restaurant owner");

        Restaurant restaurant = restaurantMapper.toDomain(inputDTO);
        return restaurantGateway.save(restaurant);
    }

    // verifica se o usuário que sera vinculado ao restaurante é o proprio requester, caso não, busca o user que será vinculado ao restaurante
    private User resolveOwnerUser(UUID ownerUserId, User requester) {
        if (requester.getId() != null && requester.getId().equals(ownerUserId)) {
            return requester;
        }

        return userGateway.findById(ownerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Owner user not found"));
    }
}
