package com.fiap.mariacomanda.infrastructure.database.mapper.restaurant.impl;

import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do RestaurantEntityMapperImpl")
class RestaurantEntityMapperImplTest {

    private RestaurantEntityMapperImpl mapper;
    private UUID restaurantId;
    private UUID ownerUserId;

    @BeforeEach
    void setUp() {
        mapper = new RestaurantEntityMapperImpl();
        restaurantId = UUID.randomUUID();
        ownerUserId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve mapear Restaurant (domain) para RestaurantEntity corretamente")
    void deveMapearDomainParaEntityCorretamente() {
        Restaurant domain = new Restaurant(
                restaurantId,
                "Restaurante Teste",
                "Rua X, 123",
                "Italiana",
                "SEG-DOM:10:00-22:00",
                ownerUserId
        );

        RestaurantEntity entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(restaurantId, entity.getId());
        assertEquals("Restaurante Teste", entity.getName());
        assertEquals("Rua X, 123", entity.getAddress());
        assertEquals("Italiana", entity.getCuisineType());
        assertEquals("SEG-DOM:10:00-22:00", entity.getOpeningHours());
        assertEquals(ownerUserId, entity.getOwnerUserId());
    }

    @Test
    @DisplayName("Deve mapear RestaurantEntity para Restaurant (domain) corretamente")
    void deveMapearEntityParaDomainCorretamente() {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(restaurantId);
        entity.setName("Restaurante Demo");
        entity.setAddress("Av. Central, 456");
        entity.setCuisineType("Japonesa");
        entity.setOpeningHours("SEG-SEX:11:00-23:00");
        entity.setOwnerUserId(ownerUserId);

        Restaurant domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(restaurantId, domain.getId());
        assertEquals("Restaurante Demo", domain.getName());
        assertEquals("Av. Central, 456", domain.getAddress());
        assertEquals("Japonesa", domain.getCuisineTypeForDisplay());
        assertEquals("SEG-SEX:11:00-23:00", domain.getOpeningHoursForStorage());
        assertEquals(ownerUserId, domain.getOwnerUserId());
    }

    @Test
    @DisplayName("Deve mapear lista de RestaurantEntity para lista de Restaurant corretamente")
    void deveMapearListaEntityParaDomain() {
        RestaurantEntity e1 = new RestaurantEntity();
        e1.setId(UUID.randomUUID());
        e1.setName("R1");
        e1.setAddress("Endereço 1");
        e1.setCuisineType("Brasileira");
        e1.setOpeningHours("10:00-22:00");
        e1.setOwnerUserId(ownerUserId);

        RestaurantEntity e2 = new RestaurantEntity();
        e2.setId(UUID.randomUUID());
        e2.setName("R2");
        e2.setAddress("Endereço 2");
        e2.setCuisineType("Chinesa");
        e2.setOpeningHours("SEG-SEX:11:00-23:00");
        e2.setOwnerUserId(ownerUserId);

        List<Restaurant> domains = mapper.toDomainList(List.of(e1, e2));

        assertEquals(2, domains.size());
        assertEquals("R1", domains.get(0).getName());
        assertEquals("R2", domains.get(1).getName());
    }

    @Test
    @DisplayName("Deve mapear lista de Restaurant para lista de RestaurantEntity corretamente")
    void deveMapearListaDomainParaEntity() {
        Restaurant r1 = new Restaurant(
                UUID.randomUUID(),
                "Restaurante 1",
                "Endereço 1",
                "Mexicana",
                "10:00-22:00",
                ownerUserId
        );

        Restaurant r2 = new Restaurant(
                UUID.randomUUID(),
                "Restaurante 2",
                "Endereço 2",
                "Francesa",
                "SEG-SEX:11:00-23:00",
                ownerUserId
        );

        List<RestaurantEntity> entities = mapper.toEntityList(List.of(r1, r2));

        assertEquals(2, entities.size());
        assertEquals("Restaurante 1", entities.get(0).getName());
        assertEquals("Restaurante 2", entities.get(1).getName());
    }

    @Test
    @DisplayName("Deve lançar exceção ao usar openingHours inválido no domain")
    void deveLancarExcecaoParaOpeningHoursInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Restaurant(
                    UUID.randomUUID(),
                    "Restaurante Inválido",
                    "Rua Teste",
                    "Italiana",
                    "9-22", // ❌ formato inválido
                    ownerUserId
            );
        });
    }
}
