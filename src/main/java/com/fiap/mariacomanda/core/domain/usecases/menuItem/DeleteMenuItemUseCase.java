package com.fiap.mariacomanda.core.domain.usecases.menuItem;

import java.util.UUID;

public interface DeleteMenuItemUseCase {
    void execute(UUID id, UUID requesterUserId);
}
