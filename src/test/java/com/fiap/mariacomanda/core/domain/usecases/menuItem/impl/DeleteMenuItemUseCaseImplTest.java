package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeleteMenuItemUseCaseImplTest {

    private final MenuItemGateway gateway = mock(MenuItemGateway.class);
    private final DeleteMenuItemUseCaseImpl useCase = new DeleteMenuItemUseCaseImpl(gateway);

    @Test
    @DisplayName("Deve deletar MenuItem pelo ID")
    void execute_deveDeletarMenuItemComSucesso() {
        UUID id = UUID.randomUUID();

        useCase.execute(id);

        verify(gateway).deleteById(id);
    }
}
