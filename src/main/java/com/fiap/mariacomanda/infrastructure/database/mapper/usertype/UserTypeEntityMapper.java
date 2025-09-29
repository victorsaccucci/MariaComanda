package com.fiap.mariacomanda.infrastructure.database.mapper.usertype;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import org.springframework.stereotype.Component;

@Component("userTypeEntityMapper")
public class UserTypeEntityMapper {
    public UserTypeEntity toEntity(UserType d) {
        UserTypeEntity e = new UserTypeEntity();
        e.setId(d.getId());
        e.setName(d.getTypeName());
        return e;
    }

    public UserType toDomain(UserTypeEntity e) {
        return new UserType(
                e.getId(),
                e.getName());
    }
}