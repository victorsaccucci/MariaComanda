package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;

import java.util.List;

public interface MenuItemEntityMapper {

    MenuItemEntity toEntity(MenuItem domain);

    MenuItem toDomain(MenuItemEntity entity);

    List<MenuItem> toDomainList(List<MenuItemEntity> entities);

    List<MenuItemEntity> toEntityList(List<MenuItem> domains);
}
