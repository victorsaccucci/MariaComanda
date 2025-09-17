package com.fiap.mariacomanda.core.usecases.menuItem;

import java.util.UUID;
import com.fiap.mariacomanda.core.gateway.MenuItemGateway;

public class DeleteMenuItemUseCaseImpl implements DeleteMenuItemUseCase {

    private final MenuItemGateway repository;

    public DeleteMenuItemUseCaseImpl(MenuItemGateway repository){
        this.repository = repository;
    }

    @Override
    public void execute(UUID id) {
        repository.deleteById(id);
    }
}
