package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.CreateMenuItemUseCase;

public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {

    private final MenuItemGateway repository;

    public CreateMenuItemUseCaseImpl(MenuItemGateway repository) {
        this.repository = repository;
    }

    @Override 
    public MenuItem execute(MenuItem newMenuItem) {
        return repository.save(newMenuItem);
    }
}
