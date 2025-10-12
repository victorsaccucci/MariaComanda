package com.fiap.mariacomanda.core.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuItem {
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean dineInOnly;
    private String photoPath;

    public MenuItem(UUID id, UUID restaurantId, String name, String description,
                   BigDecimal price, boolean dineInOnly, String photoPath) {
        this.id = id;
        this.restaurantId = validateRestaurantId(restaurantId);
        this.name = validateName(name);
        this.description = description;
        this.price = validatePrice(price);
        this.dineInOnly = dineInOnly;
        this.photoPath = photoPath;
    }

    public MenuItem(UUID restaurantId, String name, String description,
                   BigDecimal price, boolean dineInOnly, String photoPath) {
        this.restaurantId = validateRestaurantId(restaurantId);
        this.name = validateName(name);
        this.description = description;
        this.price = validatePrice(price);
        this.dineInOnly = dineInOnly;
        this.photoPath = photoPath;
    }

    // Validações de negócio
    private UUID validateRestaurantId(UUID restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }
        return restaurantId;
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("MenuItem name cannot be null or empty");
        }
        if (name.length() > 120) {
            throw new IllegalArgumentException("MenuItem name cannot exceed 120 characters");
        }
        return name.trim();
    }

    private BigDecimal validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        return price;
    }

    // Setters com validação
    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = validateRestaurantId(restaurantId);
    }

    public void setName(String name) {
        this.name = validateName(name);
    }

    public void setPrice(BigDecimal price) {
        this.price = validatePrice(price);
    }
}