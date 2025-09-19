package com.fiap.mariacomanda.core.dto.menuItem;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateMenuItemOutputDTO {

    private UUID id;
    private UUID restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean dineInOnly; 
    private String photoPath;

    public UUID getId() {
        return id;
    }
    public UUID getRestaurantId() {
        return restaurantId;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public boolean isDineInOnly() {
        return dineInOnly;
    }
    public String getPhotoPath() {
        return photoPath;
    }

    public CreateMenuItemOutputDTO(UUID id, UUID restaurantId, String name, String description, BigDecimal price,
            boolean dineInOnly, String photoPath) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dineInOnly = dineInOnly;
        this.photoPath = photoPath;
    }

}
