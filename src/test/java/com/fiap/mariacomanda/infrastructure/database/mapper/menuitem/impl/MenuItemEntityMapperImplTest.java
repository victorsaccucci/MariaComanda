package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MenuItemEntityMapperImplTest {

    private MenuItemEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new MenuItemEntityMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear MenuItem (domain) para MenuItemEntity corretamente")
    void deveMapearDomainParaEntity() {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        MenuItem domain = new MenuItem(
                id,
                restaurantId,
                "Pizza Calabresa",
                "Pizza deliciosa",
                new BigDecimal("45.00"),
                true,
                "foto.jpg"
        );

        MenuItemEntity entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getRestaurantId(), entity.getRestaurantId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getDescription(), entity.getDescription());
        assertEquals(domain.getPrice(), entity.getPrice());
        assertEquals(domain.isDineInOnly(), entity.isDineInOnly());
        assertEquals(domain.getPhotoPath(), entity.getPhotoPath());
    }

    @Test
    @DisplayName("Deve mapear MenuItemEntity para MenuItem corretamente")
    void deveMapearEntityParaDomain() {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(id);
        entity.setRestaurantId(restaurantId);
        entity.setName("Burger");
        entity.setDescription("Hambúrguer artesanal");
        entity.setPrice(new BigDecimal("30.00"));
        entity.setDineInOnly(false);
        entity.setPhotoPath("burger.jpg");

        MenuItem domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getRestaurantId(), domain.getRestaurantId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getDescription(), domain.getDescription());
        assertEquals(entity.getPrice(), domain.getPrice());
        assertEquals(entity.isDineInOnly(), domain.isDineInOnly());
        assertEquals(entity.getPhotoPath(), domain.getPhotoPath());
    }

    @Test
    @DisplayName("Deve mapear MenuItemEntity para MenuItem corretamente, incluindo restaurant não nulo")
    void deveMapearEntityParaDomainComRestaurant() {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UUID restaurantObjectId = UUID.randomUUID();

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantObjectId);

        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(id);
        entity.setRestaurantId(restaurantId);
        entity.setRestaurant(restaurant);
        entity.setName("Burger");
        entity.setDescription("Hambúrguer artesanal");
        entity.setPrice(new BigDecimal("30.00"));
        entity.setDineInOnly(false);
        entity.setPhotoPath("burger.jpg");

        MenuItem domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(restaurantObjectId, domain.getRestaurantId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getDescription(), domain.getDescription());
        assertEquals(entity.getPrice(), domain.getPrice());
        assertEquals(entity.isDineInOnly(), domain.isDineInOnly());
        assertEquals(entity.getPhotoPath(), domain.getPhotoPath());
    }


    @Test
    @DisplayName("Deve mapear lista de MenuItemEntity para lista de MenuItem corretamente")
    void deveMapearListaDeEntitiesParaListaDeDomains() {
        MenuItemEntity e1 = new MenuItemEntity();
        e1.setId(UUID.randomUUID());
        e1.setName("Pizza");
        e1.setDescription("Pizza sabor calabresa");
        e1.setPrice(new BigDecimal("45.00"));
        e1.setRestaurantId(UUID.randomUUID());
        e1.setDineInOnly(true);
        e1.setPhotoPath("pizza.jpg");

        MenuItemEntity e2 = new MenuItemEntity();
        e2.setId(UUID.randomUUID());
        e2.setName("Lasanha");
        e2.setDescription("Lasanha de carne");
        e2.setPrice(new BigDecimal("35.00"));
        e2.setRestaurantId(UUID.randomUUID());
        e2.setDineInOnly(false);
        e2.setPhotoPath("lasanha.jpg");

        List<MenuItem> domains = mapper.toDomainList(List.of(e1, e2));

        assertEquals(2, domains.size());
        assertEquals("Pizza", domains.get(0).getName());
        assertEquals("Lasanha", domains.get(1).getName());
    }

    @Test
    @DisplayName("Deve mapear lista de MenuItem para lista de MenuItemEntity corretamente")
    void deveMapearListaDeDomainsParaListaDeEntities() {
        MenuItem m1 = new MenuItem(UUID.randomUUID(), UUID.randomUUID(), "Coca-Cola", "Bebida gelada", new BigDecimal("7.00"), false, "coca.jpg");
        MenuItem m2 = new MenuItem(UUID.randomUUID(), UUID.randomUUID(), "Suco de Laranja", "Natural", new BigDecimal("8.00"), false, "suco.jpg");

        List<MenuItemEntity> entities = mapper.toEntityList(List.of(m1, m2));

        assertEquals(2, entities.size());
        assertEquals("Coca-Cola", entities.get(0).getName());
        assertEquals("Suco de Laranja", entities.get(1).getName());
    }
}
