package com.fiap.mariacomanda.core.usecases.menuItem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;

import java.util.Optional;
import java.util.UUID;

public interface GetMenuItemUseCase {

    Optional<MenuItem> execute(UUID id);
}
