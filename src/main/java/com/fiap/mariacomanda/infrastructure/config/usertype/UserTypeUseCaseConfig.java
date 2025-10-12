package com.fiap.mariacomanda.infrastructure.config.usertype;

import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserTypeGateway;
import com.fiap.mariacomanda.core.domain.usecases.usertype.CreateUserTypeUseCase;
import com.fiap.mariacomanda.core.domain.usecases.usertype.DeleteUserTypeUseCase;
import com.fiap.mariacomanda.core.domain.usecases.usertype.GetUserTypeUseCase;
import com.fiap.mariacomanda.core.domain.usecases.usertype.ListUserTypeUseCase;
import com.fiap.mariacomanda.core.domain.usecases.usertype.UpdateUserTypeUseCase;
import com.fiap.mariacomanda.core.domain.usecases.usertype.impl.CreateUserTypeUseCaseImpl;
import com.fiap.mariacomanda.core.domain.usecases.usertype.impl.DeleteUserTypeUseCaseImpl;
import com.fiap.mariacomanda.core.domain.usecases.usertype.impl.GetUserTypeUseCaseImpl;
import com.fiap.mariacomanda.core.domain.usecases.usertype.impl.ListUserTypeUseCaseImpl;
import com.fiap.mariacomanda.core.domain.usecases.usertype.impl.UpdateUserTypeUseCaseImpl;
import com.fiap.mariacomanda.core.mapper.UserTypeMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeUseCaseConfig {

    @Bean
    public CreateUserTypeUseCase createUserTypeUsecase(UserTypeGateway userTypeGateway, UserGateway userGateway,
                                                    UserTypeMapper userTypeMapper) {
        return new CreateUserTypeUseCaseImpl(userTypeGateway, userGateway, userTypeMapper);
    }

    @Bean
    public DeleteUserTypeUseCase deleteUserTypeUsecase(UserTypeGateway userTypeGateway, UserGateway userGateway) {
        return new DeleteUserTypeUseCaseImpl(userTypeGateway, userGateway);
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
    public UpdateUserTypeUseCase updateUserTypeUsecase(UserTypeGateway userTypeGateway, UserGateway userGateway) {
        return new UpdateUserTypeUseCaseImpl(userTypeGateway, userGateway);
    }
}
