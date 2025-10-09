package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantEntityMapperImplTest {

    private RestaurantEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new RestaurantEntityMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear Restaurant (domain) para RestaurantEntity corretamente")
    void deveMapearDomainParaEntityCorretamente() {
        UUID id = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        Restaurant domain = new Restaurant(
                id,
                "Pizzaria Napoli",
                "Rua das Flores, 123",
                "Italiana",
                "18h - 23h",
                ownerId
        );

        RestaurantEntity entity = mapper.toEntity(domain);

        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getAddress(), entity.getAddress());
        assertEquals(domain.getCuisineType(), entity.getCuisineType());
        assertEquals(domain.getOpeningHours(), entity.getOpeningHours());
        assertEquals(domain.getOwnerUserId(), entity.getOwnerUserId());
    }

    @Test
    @DisplayName("Deve mapear RestaurantEntity para Restaurant (domain) corretamente")
    void deveMapearEntityParaDomainCorretamente() {
        UUID id = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(id);
        entity.setName("Sushi Place");
        entity.setAddress("Av. Japão, 456");
        entity.setCuisineType("Japonesa");
        entity.setOpeningHours("11h - 22h");
        entity.setOwnerUserId(ownerId);

        Restaurant domain = mapper.toDomain(entity);

        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getAddress(), domain.getAddress());
        assertEquals(entity.getCuisineType(), domain.getCuisineType());
        assertEquals(entity.getOpeningHours(), domain.getOpeningHours());
        assertEquals(entity.getOwnerUserId(), domain.getOwnerUserId());
    }

    @Test
    @DisplayName("Deve mapear lista de RestaurantEntity para lista de Restaurant corretamente")
    void deveMapearListaDeEntitiesParaListaDeDomains() {
        UUID ownerId = UUID.randomUUID();

        RestaurantEntity e1 = new RestaurantEntity();
        e1.setId(UUID.randomUUID());
        e1.setName("Restaurante 1");
        e1.setAddress("Rua 1, 123");
        e1.setCuisineType("Italiana");
        e1.setOpeningHours("08:00 - 20:00");
        e1.setOwnerUserId(ownerId);

        RestaurantEntity e2 = new RestaurantEntity();
        e2.setId(UUID.randomUUID());
        e2.setName("Restaurante 2");
        e2.setAddress("Rua 2, 123");
        e2.setCuisineType("Japonesa");
        e2.setOpeningHours("10:00 - 22:00");
        e2.setOwnerUserId(ownerId);

        List<Restaurant> result = mapper.toDomainList(List.of(e1, e2));

        assertEquals(2, result.size());
        assertEquals("Restaurante 1", result.get(0).getName());
        assertEquals("Restaurante 2", result.get(1).getName());
    }

    @Test
    @DisplayName("Deve mapear lista de Restaurant (domain) para lista de RestaurantEntity corretamente")
    void deveMapearListaDeDomainsParaListaDeEntities() {
        UUID ownerId = UUID.randomUUID();

        Restaurant r1 = new Restaurant(
                UUID.randomUUID(),
                "Açaí Top",
                "Rua Roxa, 10",
                "Brasileira",
                "8h - 20h",
                ownerId
        );

        Restaurant r2 = new Restaurant(
                UUID.randomUUID(),
                "Bistrô Paris",
                "Av. França, 22",
                "Francesa",
                "19h - 23h",
                ownerId
        );

        List<RestaurantEntity> result = mapper.toEntityList(List.of(r1, r2));

        assertEquals(2, result.size());
        assertEquals("Açaí Top", result.get(0).getName());
        assertEquals("Bistrô Paris", result.get(1).getName());
    }
}
