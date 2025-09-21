package com.fiap.mariacomanda.core.domain.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;

public interface CreateMenuItemUseCase {
    MenuItem execute(MenuItem menuItem);
}
