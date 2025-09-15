package com.fiap.mariacomanda.core.dto.restaurant;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GetRestaurantOutputDTO {
    private UUID id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private UUID ownerUserId;

    public GetRestaurantOutputDTO(UUID id, String name, String address, String cuisineType, String openingHours, UUID ownerUserId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.ownerUserId = ownerUserId;
    }

}
