package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import java.util.UUID;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.RestaurantValidator;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.CreateMenuItemUseCase;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;

public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {
    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;
    private final MenuItemMapper menuItemMapper;

    public CreateMenuItemUseCaseImpl(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway,
                                    UserGateway userGateway, MenuItemMapper menuItemMapper) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItem execute(CreateMenuItemInputDTO inputDTO, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(inputDTO, CreateMenuItemInputDTO.class.getName());

        RequesterValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));

        RequesterValidator.validateRequesterIsOwner(requester, "create menu items");

        NullObjectValidator.validateNotNull(inputDTO.restaurantId(), "restaurantId");
        RestaurantValidator.validateRestaurantId(inputDTO.restaurantId());
        Restaurant restaurant = restaurantGateway.findById(inputDTO.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // Garante que o solicitante seja o proprietário do restaurante ao qual o prato será vinculado
        RestaurantValidator.validateUserOwnsRestaurant(restaurant, requesterUserId);

        MenuItem menuItem = menuItemMapper.toDomain(inputDTO);
        return menuItemGateway.save(menuItem);
    }
}
