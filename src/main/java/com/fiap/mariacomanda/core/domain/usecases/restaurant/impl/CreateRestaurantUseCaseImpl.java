package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import java.util.UUID;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.CreateRestaurantUseCase;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;

public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;
    private final RestaurantMapper restaurantMapper;

    public CreateRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, UserGateway userGateway, RestaurantMapper restaurantMapper) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public Restaurant execute(CreateRestaurantInputDTO inputDTO, UUID requesterUserId) {
        if (inputDTO == null) {
            throw new IllegalArgumentException("CreateRestaurantInputDTO cannot be null");
        }
        User requester = resolveRequester(requesterUserId);
        UserType requesterType = requester.getUserType();
        // verificando se requester é do tipo dono
        if (requesterType == null || !requesterType.isOwner()) {
            throw new IllegalStateException("Only OWNER users can create restaurants");
        }

        // montando o domain
        Restaurant restaurant = restaurantMapper.toDomain(inputDTO);
        return restaurantGateway.save(restaurant);
    }

    // busca o usuário que fez a requisição
    private User resolveRequester(UUID requesterUserId) {
        if (requesterUserId == null) {
            throw new IllegalArgumentException("Requester user ID cannot be null");
        }

        return userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
    }
}
