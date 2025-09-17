package com.fiap.mariacomanda.core.dto.user;

import java.util.UUID;

public class DeleteUserInputDTO {
    private UUID id;

    public java.util.UUID getId() {
        return id;
    }

    public DeleteUserInputDTO(UUID id) {
        this.id = id;
    }
}
