package com.fiap.mariacomanda.core.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;

import java.util.List;
import java.util.UUID;

public interface ListMenuItemUseCase {

    List<MenuItem> execute(UUID restaurantId, int page, int size);
}
