package com.fiap.mariacomanda.core.dto;

import java.util.UUID;

public class DeleteRestaurantInputDTO {
    private UUID id;

    public DeleteRestaurantInputDTO(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}