package com.fiap.mariacomanda.core.gateway;

import com.fiap.mariacomanda.core.domain.entity.UserType;
import java.util.*;

public interface UserTypeGateway {
    UserType save(UserType t);
    Optional<UserType> findById(UUID id);
    List<UserType> findAll(int page, int size);
    void deleteById(UUID id);
}