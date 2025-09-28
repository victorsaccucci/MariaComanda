package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;
import org.springframework.stereotype.Component;

@Component("menuItemEntityMapper")
public class MenuItemEntityMapper {

    public MenuItemEntity toEntity(MenuItem domain) {
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(domain.getId());
        entity.setRestaurantId(domain.getRestaurantId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setDineInOnly(domain.isDineInOnly());
        entity.setPhotoPath(domain.getPhotoPath());
        return entity;
    }

    public MenuItem toDomain(MenuItemEntity entity) {
        return new MenuItem(
                entity.getId(),
                entity.getRestaurant() != null ? entity.getRestaurant().getId() : entity.getRestaurantId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.isDineInOnly(),
                entity.getPhotoPath());
    }

}
