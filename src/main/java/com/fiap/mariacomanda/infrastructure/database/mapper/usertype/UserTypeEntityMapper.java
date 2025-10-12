package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;

public interface UserTypeEntityMapper {

    UserTypeEntity toEntity(UserType d);

    UserType toDomain(UserTypeEntity e);
}