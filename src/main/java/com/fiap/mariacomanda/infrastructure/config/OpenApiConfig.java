package com.fiap.mariacomanda.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tech Challenge FIAP")
                        .description("MariaComanda - Plataforma prática para administrar e gerenciar restaurantes com facilidade.")
                        .version("v0.0.1"));
    }
}