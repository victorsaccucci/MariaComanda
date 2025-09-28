package com.fiap.mariacomanda.core.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private UUID ownerUserId;

    public Restaurant(UUID id, String name, String address, String cuisineType,
                     String openingHours, UUID ownerUserId) {
        this.id = id;
        this.name = validateName(name);
        this.address = validateAddress(address);
        this.cuisineType = validateCuisineType(cuisineType);
        this.openingHours = openingHours;
        this.ownerUserId = validateOwnerUserId(ownerUserId);
    }

    // Validações de negócio
    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or empty");
        }
        if (name.length() > 120) {
            throw new IllegalArgumentException("Restaurant name cannot exceed 120 characters");
        }
        return name.trim();
    }

    private String validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant address cannot be null or empty");
        }
        if (address.length() > 255) {
            throw new IllegalArgumentException("Restaurant address cannot exceed 255 characters");
        }
        return address.trim();
    }

    private String validateCuisineType(String cuisineType) {
        if (cuisineType == null || cuisineType.trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant cuisine type cannot be null or empty");
        }
        if (cuisineType.length() > 50) {
            throw new IllegalArgumentException("Restaurant cuisine type cannot exceed 50 characters");
        }
        return cuisineType.trim();
    }

    private UUID validateOwnerUserId(UUID ownerUserId) {
        if (ownerUserId == null) {
            throw new IllegalArgumentException("Owner User ID cannot be null");
        }
        return ownerUserId;
    }

    // Setters com validação
    public void setName(String name) {
        this.name = validateName(name);
    }

    public void setAddress(String address) {
        this.address = validateAddress(address);
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = validateCuisineType(cuisineType);
    }

    public void setOwnerUserId(UUID ownerUserId) {
        this.ownerUserId = validateOwnerUserId(ownerUserId);
    }
}