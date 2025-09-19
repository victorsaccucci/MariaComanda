package com.fiap.mariacomanda.core.dto.menuItem;

import java.util.UUID;

public class DeleteMenuItemInputDTO {

    private UUID id;

    public UUID getId() {
        return id;
    }

    public DeleteMenuItemInputDTO(UUID id) {
        this.id = id;
    }
}
