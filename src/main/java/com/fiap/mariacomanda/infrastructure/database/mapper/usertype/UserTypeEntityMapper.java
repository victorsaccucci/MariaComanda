package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;

import java.util.UUID;

public interface UserTypeEntityMapper {

    UserTypeEntity toEntity(UserType userType);

    UserType toDomain(UserTypeEntity entity);

    UserType buildUserType(UUID id, String name, String subType);
}
