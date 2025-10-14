package com.fiap.mariacomanda.core.mapper.impl;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemMapperImplTest {

    private MenuItemMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new MenuItemMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear CreateMenuItemInputDTO para MenuItem corretamente")
    void deveMapearCreateMenuItemInputDTOParaMenuItem() {
        UUID restaurantId = UUID.randomUUID();
        CreateMenuItemInputDTO dto = new CreateMenuItemInputDTO(
                "Pizza Calabresa",
                "Pizza deliciosa",
                new BigDecimal("45.00"),
                restaurantId,
                true,
                "/images/pizza.jpg"
        );
        MenuItem menuItem = mapper.toDomain(dto);

        assertNull(menuItem.getId());
        assertEquals(dto.restaurantId(), menuItem.getRestaurantId());
        assertEquals(dto.name(), menuItem.getName());
        assertEquals(dto.description(), menuItem.getDescription());
        assertEquals(dto.price(), menuItem.getPrice());
        assertEquals(dto.dineInOnly(), menuItem.isDineInOnly());
        assertEquals(dto.photoPath(), menuItem.getPhotoPath());
    }

    @Test
    @DisplayName("Deve mapear UpdateMenuItemInputDTO para MenuItem corretamente")
    void deveMapearUpdateMenuItemInputDTOParaMenuItem() {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                id,
                "Pizza Pepperoni",
                "Pizza com pepperoni",
                new BigDecimal("50.00"),
                restaurantId,
                false,
                "/images/pepperoni.jpg"
        );
        MenuItem menuItem = mapper.toDomain(dto);

        assertEquals(dto.id(), menuItem.getId());
        assertEquals(dto.restaurantId(), menuItem.getRestaurantId());
        assertEquals(dto.name(), menuItem.getName());
        assertEquals(dto.description(), menuItem.getDescription());
        assertEquals(dto.price(), menuItem.getPrice());
        assertEquals(dto.dineInOnly(), menuItem.isDineInOnly());
        assertEquals(dto.photoPath(), menuItem.getPhotoPath());
    }

    @Test
    @DisplayName("Deve mapear MenuItem para CreateMenuItemOutputDTO corretamente")
    void deveMapearMenuItemParaCreateMenuItemOutputDTO() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(
                id,
                UUID.randomUUID(),
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.99"),
                false,
                "/images/burger.jpg"
        );
        CreateMenuItemOutputDTO dto = mapper.toCreateOutput(menuItem);

        assertEquals(menuItem.getId(), dto.id());
    }

    @Test
    @DisplayName("Deve mapear MenuItem para GetMenuItemOutputDTO corretamente")
    void deveMapearMenuItemParaGetMenuItemOutputDTO() {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(
                id,
                restaurantId,
                "Pastel",
                "Pastel de queijo",
                new BigDecimal("9.50"),
                true,
                "/images/pastel.jpg"
        );
        GetMenuItemOutputDTO dto = mapper.toGetOutput(menuItem);

        assertEquals(menuItem.getId(), dto.id());
        assertEquals(menuItem.getName(), dto.name());
        assertEquals(menuItem.getDescription(), dto.description());
        assertEquals(menuItem.getPrice(), dto.price());
        assertEquals(menuItem.getRestaurantId(), dto.restaurantId());
        assertEquals(menuItem.isDineInOnly(), dto.dineInOnly());
        assertEquals(menuItem.getPhotoPath(), dto.photoPath());
    }

    @Test
    @DisplayName("Deve mapear lista de MenuItem para lista de GetMenuItemOutputDTO corretamente")
    void deveMapearListaMenuItemParaListaGetMenuItemOutputDTO() {
        MenuItem m1 = new MenuItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Refrigerante",
                "Lata",
                new BigDecimal("6.00"),
                false,
                "/images/refri.jpg"
        );
        MenuItem m2 = new MenuItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Suco",
                "Natural",
                new BigDecimal("7.00"),
                false,
                "/images/suco.jpg"
        );
        List<GetMenuItemOutputDTO> dtos = mapper.toGetOutputList(List.of(m1, m2));

        assertEquals(2, dtos.size());
        assertEquals(m1.getId(), dtos.get(0).id());
        assertEquals(m2.getId(), dtos.get(1).id());
        assertEquals(m1.getName(), dtos.get(0).name());
        assertEquals(m2.getName(), dtos.get(1).name());
    }
}
