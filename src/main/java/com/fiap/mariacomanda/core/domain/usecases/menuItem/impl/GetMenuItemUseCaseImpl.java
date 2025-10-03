package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.GetMenuItemUseCase;

import java.util.Optional;
import java.util.UUID;

public class GetMenuItemUseCaseImpl implements GetMenuItemUseCase {

    private final MenuItemGateway gateway;

    public GetMenuItemUseCaseImpl(MenuItemGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Optional<MenuItem> execute(UUID id) {
        return gateway.findById(id);
    }
}
