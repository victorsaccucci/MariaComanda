package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("userTypeEntityMapper")
public class UserTypeEntityMapper {
    public UserTypeEntity toEntity(UserType d) {
        UserTypeEntity e = new UserTypeEntity();
        e.setId(d.getId());
        e.setName(d.getName());
        e.setSubType(d.getSubType());
        return e;
    }

    public UserType toDomain(UserTypeEntity e) {
        return buildUserType(e.getId(), e.getName(), e.getSubType());
    }

    private UserType buildUserType(UUID id, String name, String subType) {
        return id == null ? new UserType(name, subType) : new UserType(id, name, subType);
    }
}