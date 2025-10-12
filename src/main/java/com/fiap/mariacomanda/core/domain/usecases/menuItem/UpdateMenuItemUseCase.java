package com.fiap.mariacomanda.core.domain.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;

import java.util.UUID;

public interface UpdateMenuItemUseCase {

    MenuItem execute(UpdateMenuItemInputDTO inputDTO, UUID requesterUserId);

}
