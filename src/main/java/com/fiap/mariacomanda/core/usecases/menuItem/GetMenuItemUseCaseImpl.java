package com.fiap.mariacomanda.core.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;

import java.util.Optional;
import java.util.UUID;

public class GetMenuItemUseCaseImpl implements GetMenuItemUseCase {

    private final MenuItemGateway repository;

    public GetMenuItemUseCaseImpl(MenuItemGateway repository) {
        this.repository = repository;
    }

    @Override
    public Optional<MenuItem> execute(UUID id) {
        return repository.findById(id);
    }
}
