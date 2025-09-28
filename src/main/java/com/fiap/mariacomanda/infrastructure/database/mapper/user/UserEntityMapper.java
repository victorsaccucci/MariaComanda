package com.fiap.mariacomanda.infrastructure.database.mapper.user;

import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.UserTypeJpaRepository;
import org.springframework.stereotype.Component;

@Component("userEntityMapper")
public class UserEntityMapper {

    private final UserTypeJpaRepository userTypeJpaRepository;

    public UserEntityMapper(UserTypeJpaRepository userTypeJpaRepository) {
        this.userTypeJpaRepository = userTypeJpaRepository;
    }
    public UserEntity toEntity(User d) {
        UserEntity e = new UserEntity();
        e.setId(d.getId());
        e.setName(d.getName());
        e.setEmail(d.getEmail());
        e.setPasswordHash(d.getPasswordHash());

        UserTypeEntity userTypeEntity = userTypeJpaRepository.getReferenceById(d.getUserTypeId());
        e.setUserTypeId(userTypeEntity);
        return e;
    }

    public User toDomain(UserEntity e) {
        return new User(
            e.getId(),
            e.getName(),
            e.getEmail(),
            e.getPasswordHash(),
            e.getUserTypeId() != null ? e.getUserTypeId().getId() : null
        );
    }
}