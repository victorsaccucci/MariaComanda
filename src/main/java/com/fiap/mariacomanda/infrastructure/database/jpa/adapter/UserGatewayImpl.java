package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.UserJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.user.UserEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    public UserGatewayImpl(UserJpaRepository userJpaRepository, UserEntityMapper userEntityMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        UserEntity saved = userJpaRepository.save(userEntity);
        return userEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(userEntityMapper::toDomain);
    }

    @Override
    public List<User> findAll(int page, int size) {
        return userJpaRepository.findAll(PageRequest.of(page, size))
                .map(userEntityMapper::toDomain)
                .getContent();
    }

    @Override
    public void deleteById(UUID id) {
        userJpaRepository.deleteById(id);
    }
}