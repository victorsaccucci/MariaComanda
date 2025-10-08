package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import java.util.UUID;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.usecases.common.AuthorizationValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.RestaurantValidator;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.CreateMenuItemUseCase;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;

public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;
    private final MenuItemMapper menuItemMapper;
    private final NullObjectValidator nullObjectValidator;
    private final AuthorizationValidator authorizationValidator;
    private final RestaurantValidator restaurantValidator;

    public CreateMenuItemUseCaseImpl(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway,
                                    UserGateway userGateway, MenuItemMapper menuItemMapper,
                                    NullObjectValidator nullObjectValidator, AuthorizationValidator authorizationValidator,
                                    RestaurantValidator restaurantValidator) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
        this.menuItemMapper = menuItemMapper;
        this.nullObjectValidator = nullObjectValidator;
        this.authorizationValidator = authorizationValidator;
        this.restaurantValidator = restaurantValidator;
    }

    @Override 
    public MenuItem execute(CreateMenuItemInputDTO inputDTO, UUID requesterUserId) {
        nullObjectValidator.validateNotNull(inputDTO, CreateMenuItemInputDTO.class.getName());

        authorizationValidator.validateRequesterAndAuthorizeOwner(requesterUserId, "create menu items");

        // verificar se o restaurante pertence ao requester
        Restaurant restaurant = restaurantValidator.findRestaurant(inputDTO.restaurantId());
        restaurantValidator.validateUserOwnsRestaurant(restaurant, requesterUserId);

        // montando o domain
        MenuItem menuItem = menuItemMapper.toDomain(inputDTO);
        return menuItemGateway.save(menuItem);
    }
}
