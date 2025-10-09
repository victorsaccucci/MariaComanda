package com.fiap.mariacomanda.infrastructure.database.mapper.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;

public interface UserEntityMapper {

    UserEntity toEntity(User user);

    User toDomain(UserEntity entity, UserType userType);
}
