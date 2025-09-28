package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("menuItemEntityMapper")
public class MenuItemEntityMapperImpl implements MenuItemEntityMapper {

    @Override
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

    @Override
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

    @Override
    public List<MenuItem> toDomainList(List<MenuItemEntity> entities) {
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<MenuItemEntity> toEntityList(List<MenuItem> domains) {
        return domains.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
