package com.fiap.mariacomanda.core.domain.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;

import java.util.UUID;

public interface CreateMenuItemUseCase {
    MenuItem execute(CreateMenuItemInputDTO inputDTO, UUID requesterUserId);
}
