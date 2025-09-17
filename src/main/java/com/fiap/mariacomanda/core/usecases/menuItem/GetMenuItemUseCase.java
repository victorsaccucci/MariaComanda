package com.fiap.mariacomanda.core.usecases.menuItem;

import java.util.Optional;
import java.util.UUID;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;

public interface GetMenuItemUseCase {

    Optional<MenuItem> execute(UUID id);
}
