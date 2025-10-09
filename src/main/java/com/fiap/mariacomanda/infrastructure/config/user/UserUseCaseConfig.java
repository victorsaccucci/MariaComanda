package com.fiap.mariacomanda.infrastructure.config.user;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.usecases.user.*;
import com.fiap.mariacomanda.core.domain.usecases.user.impl.*;
import com.fiap.mariacomanda.core.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public CreateUserUseCase createUserUsecase(UserGateway userGateway, UserTypeGateway userTypeGateway, UserMapper userMapper) {
        return new CreateUserUseCaseImpl(userGateway, userTypeGateway, userMapper);
    }

    @Bean
    public DeleteUserUseCase deleteUserUsecase(UserGateway userGateway) {
        return new DeleteUserUseCaseImpl(userGateway);
    }

    @Bean
    public GetUserUseCase getUserUsecase(UserGateway userGateway) {
        return new GetUserUseCaseImpl(userGateway);
    }

    @Bean
    public ListUserUseCase listUserUsecase(UserGateway userGateway) {
        return new ListUserUseCaseImpl(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUsecase(UserGateway userGateway, UserTypeGateway userTypeGateway) {
        return new UpdateUserUseCaseImpl(userGateway, userTypeGateway);
    }
}
