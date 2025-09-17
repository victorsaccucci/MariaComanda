package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import org.springframework.stereotype.Component;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;

@Component("menuItemEntityMapper")
public class MenuItemEntityMapper {

    public MenuItemEntity toEntity(MenuItem domain) {
        var entity = new MenuItemEntity();
        entity.setId(domain.id());
        entity.setRestaurantId(domain.restaurantId());
        entity.setName(domain.name());
        entity.setDescription(domain.description());
        entity.setPrice(domain.price());
        entity.setDineInOnly(domain.dineInOnly());
        entity.setPhotoPath(domain.photoPath());
        return entity;
    }

    public MenuItem toDomain(MenuItemEntity entity) {
        return new MenuItem(
                entity.getId(),
                entity.getRestaurantId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.isDineInOnly(),
                entity.getPhotoPath());
    }

}
