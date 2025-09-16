package com.fiap.mariacomanda.core.dto.menuItem;

import java.util.UUID;

public class ListMenuItemInputDTO {

    private UUID restaurantId;
    private int page;
    private int size;

    public UUID getRestaurantId() {
        return restaurantId;
    }
    public int getPage() {
        return page;
    }
    public int getSize() {
        return size;
    }
    
    public ListMenuItemInputDTO(UUID restaurantId, int page, int size) {
        this.restaurantId = restaurantId;
        this.page = page;
        this.size = size;
    }
    
   
    
    

    
}
