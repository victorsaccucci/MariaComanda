package com.fiap.mariacomanda.core.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;

public interface CreateMenuItemUseCase {
    MenuItem execute(MenuItem menuItem);
}
