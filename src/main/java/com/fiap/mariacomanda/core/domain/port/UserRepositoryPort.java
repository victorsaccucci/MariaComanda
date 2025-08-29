package com.fiap.mariacomanda.core.domain.port;

import com.fiap.mariacomanda.core.domain.entity.User;
import java.util.*;

public interface UserRepositoryPort {
    User save(User u);
    Optional<User> findById(UUID id);
    List<User> findAll(int page, int size);
    void deleteById(UUID id);
}