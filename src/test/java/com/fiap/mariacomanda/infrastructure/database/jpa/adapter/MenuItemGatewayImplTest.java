package com.fiap.mariacomanda.infrastructure.database.jpa.adapter;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.MenuItemEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.entity.RestaurantEntity;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.MenuItemJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.jpa.repository.RestaurantJpaRepository;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemGatewayImplTest {

    private MenuItemJpaRepository menuItemJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private MenuItemEntityMapper menuItemEntityMapper;
    private MenuItemGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        menuItemJpaRepository = mock(MenuItemJpaRepository.class);
        restaurantJpaRepository = mock(RestaurantJpaRepository.class);
        menuItemEntityMapper = mock(MenuItemEntityMapper.class);
        gateway = new MenuItemGatewayImpl(menuItemJpaRepository, restaurantJpaRepository, menuItemEntityMapper);
    }

    @Test
    @DisplayName("Deve salvar MenuItem corretamente")
    void testSaveMenuItem() {
        UUID restaurantId = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(restaurantId, "Pizza", "Deliciosa", new BigDecimal("35.50"), true, "photo.jpg");

        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(UUID.randomUUID());

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);

        when(menuItemEntityMapper.toEntity(menuItem)).thenReturn(entity);
        when(restaurantJpaRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
        when(menuItemJpaRepository.save(entity)).thenReturn(entity);
        when(menuItemEntityMapper.toDomain(entity)).thenReturn(menuItem);

        MenuItem result = gateway.save(menuItem);

        assertNotNull(result);
        assertEquals(menuItem, result);

        verify(menuItemJpaRepository).save(entity);
        verify(restaurantJpaRepository).findById(restaurantId);
    }

    @Test
    @DisplayName("Deve lançar exceção se restaurante não existir ao salvar")
    void testSaveMenuItemRestaurantNotFound() {
        UUID restaurantId = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(
                restaurantId,
                "Pizza",
                "Deliciosa pizza de calabresa",
                new BigDecimal("35.50"),
                true,
                "path/to/photo.jpg");

        when(menuItemEntityMapper.toEntity(menuItem)).thenReturn(new MenuItemEntity());
        when(restaurantJpaRepository.findById(restaurantId)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> gateway.save(menuItem));

        assertEquals("Restaurant not found for id: " + restaurantId, exception.getMessage());
    }

    @Test
    @DisplayName("Deve buscar MenuItem por ID")
    void testFindById() {
        UUID id = UUID.randomUUID();
        MenuItemEntity entity = new MenuItemEntity();
        MenuItem menuItem = new MenuItem();

        when(menuItemJpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(menuItemEntityMapper.toDomain(entity)).thenReturn(menuItem);

        Optional<MenuItem> result = gateway.findById(id);

        assertTrue(result.isPresent());
        assertEquals(menuItem, result.get());
    }

    @Test
    @DisplayName("Deve retornar lista de MenuItems de restaurante")
    void testFindByRestaurant() {
        UUID restaurantId = UUID.randomUUID();
        MenuItemEntity entity = new MenuItemEntity();
        MenuItem menuItem = new MenuItem();

        when(menuItemJpaRepository.findByRestaurant_Id(eq(restaurantId), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(entity)));

        when(menuItemEntityMapper.toDomain(entity)).thenReturn(menuItem);

        List<MenuItem> result = gateway.findByRestaurant(restaurantId, 0, 10);

        assertEquals(1, result.size());
        assertEquals(menuItem, result.get(0));
    }


    @Test
    @DisplayName("Deve deletar MenuItem por ID")
    void testDeleteById() {
        UUID id = UUID.randomUUID();

        gateway.deleteById(id);

        verify(menuItemJpaRepository).deleteById(id);
    }
}
