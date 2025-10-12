package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import java.math.BigDecimal;
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

        NullObjectValidator.validateNotNull(inputDTO.restaurantId(), "Restaurant ID");
        NullObjectValidator.validateNotNull(inputDTO.name(), "Menu item name");
        if (inputDTO.name() != null && inputDTO.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Menu item name cannot be empty");
        }
        NullObjectValidator.validateNotNull(inputDTO.description(), "Menu item description");
        if (inputDTO.description() != null && inputDTO.description().trim().isEmpty()) {
            throw new IllegalArgumentException("Menu item description cannot be empty");
        }
        NullObjectValidator.validateNotNull(inputDTO.dineInOnly(), "Dine in only flag");
        NullObjectValidator.validateNotNull(inputDTO.photoPath(), "Photo path");
        if (inputDTO.photoPath() != null && inputDTO.photoPath().trim().isEmpty()) {
            throw new IllegalArgumentException("Photo path cannot be empty");
        }
        if (inputDTO.photoPath() != null && !inputDTO.photoPath().matches("^/images/.*\\.(jpg|jpeg|png|gif|webp)$")) {
            throw new IllegalArgumentException("Photo path must be a valid image path (e.g., /images/photo.jpg)");
        }
        NullObjectValidator.validateNotNull(inputDTO.price(), "Menu item price");
        if (inputDTO.price() != null && inputDTO.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Menu item price must be greater than zero");
        }
        RestaurantValidator.validateRestaurantId(inputDTO.restaurantId());
        Restaurant restaurant = restaurantGateway.findById(inputDTO.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // Garante que o solicitante seja o proprietário do restaurante ao qual o prato será vinculado
        RestaurantValidator.validateUserOwnsRestaurant(restaurant, requesterUserId);

        MenuItem menuItem = menuItemMapper.toDomain(inputDTO);
        return menuItemGateway.save(menuItem);
    }
}
