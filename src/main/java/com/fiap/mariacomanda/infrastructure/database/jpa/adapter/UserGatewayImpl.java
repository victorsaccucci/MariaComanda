package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.UserJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.UserTypeJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.user.UserEntityMapper;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.UserTypeEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;
    private final UserTypeJpaRepository userTypeJpaRepository;
    private final UserTypeEntityMapper userTypeEntityMapper;

    public UserGatewayImpl(UserJpaRepository userJpaRepository,
                           UserEntityMapper userEntityMapper,
                           UserTypeJpaRepository userTypeJpaRepository,
                           UserTypeEntityMapper userTypeEntityMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userEntityMapper = userEntityMapper;
        this.userTypeJpaRepository = userTypeJpaRepository;
        this.userTypeEntityMapper = userTypeEntityMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        UserEntity saved = userJpaRepository.save(userEntity);
        return userEntityMapper.toDomain(saved, user.getUserType());
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(this::toDomainWithUserType);
    }

    @Override
    public List<User> findAll(int page, int size) {
        return userJpaRepository.findAll(PageRequest.of(page, size))
                .map(this::toDomainWithUserType)
                .getContent();
    }

    @Override
    public void deleteById(UUID id) {
        userJpaRepository.deleteById(id);
    }

    // Compõe UserType dentro de User
    private User toDomainWithUserType(UserEntity entity) {
        UserType userType = userTypeJpaRepository.findById(entity.getUserTypeId())
                .map(userTypeEntityMapper::toDomain)
                .orElseThrow(() -> new IllegalStateException("UserType not found for id " + entity.getUserTypeId()));

        return userEntityMapper.toDomain(entity, userType);
    }
}
