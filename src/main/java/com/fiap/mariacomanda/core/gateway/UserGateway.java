package com.fiap.mariacomanda.core.gateway;

import com.fiap.mariacomanda.core.domain.entity.User;
import java.util.*;

public interface UserGateway {
    User save(User u);
    Optional<User> findById(UUID id);
    List<User> findAll(int page, int size);
    void deleteById(UUID id);
}