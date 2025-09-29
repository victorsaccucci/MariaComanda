package com.fiap.mariacomanda.infrastructure.config.usertype;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.usecases.user.*;
import com.fiap.mariacomanda.core.domain.usecases.user.impl.*;
import com.fiap.mariacomanda.core.domain.usecases.usertype.*;
import com.fiap.mariacomanda.core.domain.usecases.usertype.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeUseCaseConfig {

    @Bean
    public CreateUserTypeUseCase createUserTypeUsecase(UserTypeGateway userTypeGateway) {
        return new CreateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public DeleteUserTypeUseCase deleteUserTypeUsecase(UserTypeGateway userTypeGateway) {
        return new DeleteUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public GetUserTypeUseCase getUserTypeUsecase(UserTypeGateway userTypeGateway) {
        return new GetUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public ListUserTypeUseCase listUserTypeUsecase(UserTypeGateway userTypeGateway) {
        return new ListUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public UpdateUserTypeUseCase updateUserTypeUsecase(UserTypeGateway userTypeGateway) {
        return new UpdateUserTypeUseCaseImpl(userTypeGateway);
    }
}
