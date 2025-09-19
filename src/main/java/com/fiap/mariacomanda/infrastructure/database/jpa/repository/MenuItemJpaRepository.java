package com.fiap.mariacomanda.infrastructure.database.jpa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, UUID> {

}
