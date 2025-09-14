package com.fiap.mariacomanda.core.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteRestaurantInputDTO {
    private UUID id;

    public DeleteRestaurantInputDTO(UUID id) {
        this.id = id;
    }

}