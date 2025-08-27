package com.fiap.mariacomanda.domain.port;

import com.fiap.mariacomanda.domain.entity.UserType;
import java.util.*;

public interface UserTypeRepositoryPort {
    UserType save(UserType t);
    Optional<UserType> findById(UUID id);
    List<UserType> findAll(int page, int size);
    void deleteById(UUID id);
}