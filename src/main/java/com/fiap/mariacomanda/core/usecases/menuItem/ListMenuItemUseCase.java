package com.fiap.mariacomanda.core.usecases.menuItem;

import java.util.List;
import java.util.UUID;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;

public interface ListMenuItemUseCase {

    List<MenuItem> execute(UUID restaurantId, int page, int size);
}
