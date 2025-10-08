package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import java.util.UUID;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.usecases.common.AuthorizationValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.restaurant.CreateRestaurantUseCase;
import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.mapper.RestaurantMapper;

public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;
    private final RestaurantMapper restaurantMapper;
    private final NullObjectValidator nullObjectValidator;
    private final AuthorizationValidator authorizationValidator;

    public CreateRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, UserGateway userGateway,
                                    RestaurantMapper restaurantMapper, NullObjectValidator nullObjectValidator,
                                    AuthorizationValidator authorizationValidator) {
        this.restaurantGateway = restaurantGateway;
        this.restaurantMapper = restaurantMapper;
        this.nullObjectValidator = nullObjectValidator;
        this.authorizationValidator = authorizationValidator;
    }

    @Override
    public Restaurant execute(CreateRestaurantInputDTO inputDTO, UUID requesterUserId) {
        nullObjectValidator.validateNotNull(inputDTO, CreateRestaurantInputDTO.class.getName());

        authorizationValidator.validateRequesterAndAuthorizeOwner(requesterUserId, "create restaurants");

        // montando o domain
        Restaurant restaurant = restaurantMapper.toDomain(inputDTO);
        return restaurantGateway.save(restaurant);
    }
}
