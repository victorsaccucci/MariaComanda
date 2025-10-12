package com.fiap.mariacomanda.infrastructure.config.usertype;

import com.fiap.mariacomanda.core.adapters.controller.UserTypeController;
import com.fiap.mariacomanda.core.domain.usecases.usertype.*;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;
import com.fiap.mariacomanda.core.mapper.impl.UserTypeMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeControllerConfig {

    @Bean
    public UserTypeMapper userTypeMapper() {
        return new UserTypeMapperImpl();
    }

    @Bean
    public UserTypeController userTypeController(
            CreateUserTypeUseCase create,
            GetUserTypeUseCase get,
            ListUserTypeUseCase list,
            UpdateUserTypeUseCase update,
            DeleteUserTypeUseCase delete,
            UserTypeMapper userMapper
    ) {
        return new UserTypeController(create, get, list, update, delete, userMapper);
    }
}

