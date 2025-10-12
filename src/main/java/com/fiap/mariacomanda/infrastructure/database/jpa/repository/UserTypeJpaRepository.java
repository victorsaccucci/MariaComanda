package com.fiap.mariacomanda.infrastructure.database.jpa.repository;

import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTypeJpaRepository extends JpaRepository<UserTypeEntity, UUID> {
}