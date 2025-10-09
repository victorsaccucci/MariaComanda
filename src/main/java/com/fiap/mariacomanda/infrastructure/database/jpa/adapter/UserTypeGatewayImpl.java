package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.UserTypeEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.UserTypeJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.usertype.impl.UserTypeEntityMapperImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserTypeGatewayImpl implements UserTypeGateway {

    private final UserTypeJpaRepository userTypeJpaRepository;
    private final UserTypeEntityMapperImpl userTypeEntityMapperImpl;

    public UserTypeGatewayImpl(UserTypeJpaRepository userTypeJpaRepository, UserTypeEntityMapperImpl userTypeEntityMapperImpl
    ) {
        this.userTypeJpaRepository = userTypeJpaRepository;
        this.userTypeEntityMapperImpl = userTypeEntityMapperImpl
        ;
    }

    @Override
    public UserType save(UserType userType) {
        UserTypeEntity userTypeEntity = userTypeEntityMapperImpl.toEntity(userType);
        UserTypeEntity saved = userTypeJpaRepository.save(userTypeEntity);
        return userTypeEntityMapperImpl.toDomain(saved);
    }

    @Override
    public Optional<UserType> findById(UUID id) {
        return userTypeJpaRepository.findById(id).map(userTypeEntityMapperImpl::toDomain);
    }

    @Override
    public List<UserType> findAll(int page, int size) {
        return userTypeJpaRepository.findAll(PageRequest.of(page, size))
                .map(userTypeEntityMapperImpl::toDomain)
                .getContent();
    }

    @Override
    public void deleteById(UUID id) {
        userTypeJpaRepository.deleteById(id);
    }
}
