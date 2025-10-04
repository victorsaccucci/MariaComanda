package com.fiap.mariacomanda.infrastructure.database.mapper.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.UserTypeEntityMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component("userEntityMapper")
public class UserEntityMapper {

    private final UserTypeEntityMapper userTypeEntityMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntityMapper(UserTypeEntityMapper userTypeEntityMapper) {
        this.userTypeEntityMapper = userTypeEntityMapper;
    }

    public UserEntity toEntity(User d) {
        UserEntity e = new UserEntity();
        e.setId(d.getId());
        e.setName(d.getName());
        e.setEmail(d.getEmail());
        e.setPasswordHash(d.getPasswordHash());

        UserTypeEntity userTypeEntity = mapUserTypeToEntity(d);
        e.setUserTypeId(userTypeEntity);
        return e;
    }

    public User toDomain(UserEntity e) {
        UserTypeEntity userTypeEntity = e.getUserTypeId();
        com.fiap.mariacomanda.core.domain.entity.UserType userType = userTypeEntity != null
                ? userTypeEntityMapper.toDomain(userTypeEntity)
                : null;
        return new User(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getPasswordHash(),
                userType
        );
    }

    private UserTypeEntity mapUserTypeToEntity(User user) {
        if (user.getUserType() == null) {
            throw new IllegalArgumentException("UserType cannot be null when mapping to entity");
        }
        if (user.getUserType().getId() == null) {
            throw new IllegalArgumentException("UserType ID cannot be null when mapping to entity");
        }
        return entityManager.getReference(UserTypeEntity.class, user.getUserType().getId());
    }
}