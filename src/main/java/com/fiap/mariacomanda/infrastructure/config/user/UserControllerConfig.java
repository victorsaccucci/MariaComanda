package com.fiap.mariacomanda.infrastructure.config.user;

import com.fiap.mariacomanda.core.adapters.controller.UserController;
import com.fiap.mariacomanda.core.domain.usecases.user.*;
import com.fiap.mariacomanda.core.mapper.UserMapper;
import com.fiap.mariacomanda.core.mapper.impl.UserMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserControllerConfig {

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public UserController userController(
            CreateUserUseCase create,
            GetUserUseCase get,
            ListUserUseCase list,
            UpdateUserUseCase update,
            DeleteUserUseCase delete,
            UserMapper userMapper
    ) {
        return new UserController(create, get, list, update, delete, userMapper);
    }
}

