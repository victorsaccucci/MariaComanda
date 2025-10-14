package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.impl;

import com.fiap.mariacomanda.core.dto.restaurant.input.CreateRestaurantInputDTO;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import com.fiap.mariacomanda.infrastructure.web.json.CreateRestaurantJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateRestaurantJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestaurantJsonMapperImplTest {

    private RestaurantJsonMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new RestaurantJsonMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear CreateRestaurantJson para CreateRestaurantInputDTO corretamente")
    void deveMapearRestaurantJsonParaCreateRestaurantInputDTO() throws Exception {
        CreateRestaurantJson json = new CreateRestaurantJson();

        setPrivateField(json, "name", "Restaurante Bom Sabor");
        setPrivateField(json, "address", "Rua das Flores, 123");
        setPrivateField(json, "cuisineType", "Italiana");
        setPrivateField(json, "openingHours", "10:00 - 22:00");
        setPrivateField(json, "ownerUserId", UUID.randomUUID());

        CreateRestaurantInputDTO dto = mapper.toCreateInput(json);

        assertEquals("Restaurante Bom Sabor", dto.name());
        assertEquals("Rua das Flores, 123", dto.address());
        assertEquals("Italiana", dto.cuisineType());
        assertEquals("10:00 - 22:00", dto.openingHours());
        assertNotNull(dto.ownerUserId());
    }

    @Test
    @DisplayName("Deve mapear UpdateRestaurantJson para UpdateRestaurantInputDTO corretamente")
    void deveMapearUpdateRestaurantJsonParaUpdateInputDTO() throws Exception {
        UpdateRestaurantJson json = new UpdateRestaurantJson();
        UUID restaurantId = UUID.randomUUID();
        UUID ownerUserId = UUID.randomUUID();

        setPrivateField(json, "name", "Restaurante Novo Sabor");
        setPrivateField(json, "address", "Avenida Central, 456");
        setPrivateField(json, "cuisineType", "Brasileira");
        setPrivateField(json, "openingHours", "11:00-23:00");
        setPrivateField(json, "ownerUserId", ownerUserId);

        UpdateRestaurantInputDTO dto = mapper.toUpdateInput(restaurantId, json);

        assertEquals(restaurantId, dto.id());
        assertEquals("Restaurante Novo Sabor", dto.name());
        assertEquals("Avenida Central, 456", dto.address());
        assertEquals("Brasileira", dto.cuisineType());
        assertEquals("11:00-23:00", dto.openingHours());
        assertEquals(ownerUserId, dto.ownerUserId());
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
