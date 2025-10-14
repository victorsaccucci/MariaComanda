package com.fiap.mariacomanda.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    void testOpenApiConfig() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.customOpenAPI();
        
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Tech Challenge FIAP", openAPI.getInfo().getTitle());
        assertEquals("MariaComanda - Plataforma prática para administrar e gerenciar restaurantes com facilidade.", openAPI.getInfo().getDescription());
        assertEquals("v0.0.1", openAPI.getInfo().getVersion());
    }
}