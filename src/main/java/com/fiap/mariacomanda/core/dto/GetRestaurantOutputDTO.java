package com.fiap.mariacomanda.core.dto;

import java.util.UUID;

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

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCuisineType() { return cuisineType; }
    public String getOpeningHours() { return openingHours; }
    public UUID getOwnerUserId() { return ownerUserId; }
}
