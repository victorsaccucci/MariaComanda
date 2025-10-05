package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import java.util.UUID;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.CreateMenuItemUseCase;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;

public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;
    private final MenuItemMapper menuItemMapper;

    public CreateMenuItemUseCaseImpl(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway, UserGateway userGateway, MenuItemMapper menuItemMapper) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
        this.menuItemMapper = menuItemMapper;
    }

    @Override 
    public MenuItem execute(CreateMenuItemInputDTO inputDTO, UUID requesterUserId) {
        if (inputDTO == null) {
            throw new IllegalArgumentException("CreateMenuItemInputDTO cannot be null");
        }
        User requester = resolveRequester(requesterUserId);
        UserType requesterType = requester.getUserType();
        // verificando se requester é do tipo dono
        if (requesterType == null || !requesterType.isOwner()) {
            throw new IllegalStateException("Only OWNER users can create menu items");
        }

        // verificar se o restaurante pertence ao requester
        Restaurant restaurant = resolveRestaurant(inputDTO.restaurantId());
        if (!restaurant.getOwnerUserId().equals(requesterUserId)) {
            throw new IllegalStateException("Requester does not own the restaurant");
        }

        // montando o domain
        MenuItem menuItem = menuItemMapper.toDomain(inputDTO);
        return menuItemGateway.save(menuItem);
    }

    // busca o usuário que fez a requisição
    private User resolveRequester(UUID requesterUserId) {
        if (requesterUserId == null) {
            throw new IllegalArgumentException("Requester user ID cannot be null");
        }

        return userGateway.findById(requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester user not found"));
    }

    // busca o restaurante
    private Restaurant resolveRestaurant(UUID restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }

        return restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
    }
}
