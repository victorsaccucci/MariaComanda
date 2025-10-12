package com.fiap.mariacomanda.infrastructure.database.mapper.user.impl;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import com.fiap.mariacomanda.infrastructure.database.mapper.user.UserEntityMapper;
import org.springframework.stereotype.Component;

@Component("userEntityMapper")
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPasswordHash(user.getPasswordHash());
        userEntity.setUserTypeId(user.getUserType().getId());
        return userEntity;
    }

    @Override
    public User toDomain(UserEntity entity, UserType userType) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                userType
        );
    }
}
