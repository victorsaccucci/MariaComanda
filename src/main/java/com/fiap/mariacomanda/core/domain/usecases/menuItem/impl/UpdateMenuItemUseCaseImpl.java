package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.usecases.common.RequesterValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.NullObjectValidator;
import com.fiap.mariacomanda.core.domain.usecases.common.RestaurantValidator;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.UpdateMenuItemUseCase;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public UpdateMenuItemUseCaseImpl(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway,
                                     UserGateway userGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    @Override
    public MenuItem execute(UpdateMenuItemInputDTO inputDTO, UUID requesterUserId) {
        NullObjectValidator.validateNotNull(inputDTO, UpdateMenuItemInputDTO.class.getName());
        NullObjectValidator.validateNotNull(inputDTO.id(), "menuItemId");

        RequesterValidator.validateRequesterUserId(requesterUserId);
        User requester = userGateway.findById(requesterUserId)
                .orElseThrow(() -> new EntityNotFoundException("Requester user not found", requesterUserId.toString()));
        RequesterValidator.validateRequesterIsOwner(requester, "update menu items");

        // menuitem existente a ser alterado
        MenuItem existing = menuItemGateway.findById(inputDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("MenuItem not found", inputDTO.id().toString()));

        // restaurante no qual menuitem está
        Restaurant existingRestaurant = restaurantGateway.findById(existing.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found for menu item", existing.getRestaurantId().toString()));
        // verifica se o requester é dono desse restaurante especifico (só o dono do restaurante pode atualizar seus items)
        RestaurantValidator.validateUserOwnsRestaurant(existingRestaurant, requesterUserId);

        UUID restaurantId = inputDTO.restaurantId() != null ? inputDTO.restaurantId() : existing.getRestaurantId();
        Restaurant targetRestaurant = existingRestaurant;

        if (!existing.getRestaurantId().equals(restaurantId)) {
            RestaurantValidator.validateRestaurantId(restaurantId);
            targetRestaurant = restaurantGateway.findById(restaurantId)
                    .orElseThrow(() -> new EntityNotFoundException("Restaurant not found", restaurantId.toString()));
            RestaurantValidator.validateUserOwnsRestaurant(targetRestaurant, requesterUserId);
        }

        MenuItem merged = new MenuItem(
            existing.getId(),
            targetRestaurant.getId(),
            updateValue(inputDTO.name(), existing.getName()),
            updateValue(inputDTO.description(), existing.getDescription()),
            updateValue(inputDTO.price(), existing.getPrice()),
            updateValue(inputDTO.dineInOnly(), existing.isDineInOnly()),
            updateValue(inputDTO.photoPath(), existing.getPhotoPath())
        );

        return menuItemGateway.save(merged);
    }

    private String updateValue(String newValue, String current) {
        return newValue != null ? newValue : current;
    }

    private BigDecimal updateValue(BigDecimal newValue, BigDecimal current) {
        return newValue != null ? newValue : current;
    }

    protected Boolean updateValue(Boolean newValue, Boolean current) {
        return newValue != null ? newValue : current;
    }
}
