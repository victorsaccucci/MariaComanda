package com.fiap.mariacomanda.core.adapters.gateway;

import com.fiap.mariacomanda.core.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    User save(User u);
    Optional<User> findById(UUID id);
    List<User> findAll(int page, int size);
    void deleteById(UUID id);
}