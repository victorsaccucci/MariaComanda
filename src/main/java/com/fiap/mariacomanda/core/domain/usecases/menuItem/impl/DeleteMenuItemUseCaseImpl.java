package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.DeleteMenuItemUseCase;

import java.util.UUID;

public class DeleteMenuItemUseCaseImpl implements DeleteMenuItemUseCase {

    private final MenuItemGateway repository;

    public DeleteMenuItemUseCaseImpl(MenuItemGateway repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID id) {
        repository.deleteById(id);
    }
}
