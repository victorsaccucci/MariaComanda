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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                "Pizza",
                "Deliciosa pizza de calabresa",
                new BigDecimal("35.50"),
                restaurantId,
                true,
                "path/to/photo.jpg"
        );

        MenuItem domain = mapper.toDomain(dto);

        assertEquals(dto.restaurantId(), domain.getRestaurantId());
        assertEquals(dto.name(), domain.getName());
        assertEquals(dto.description(), domain.getDescription());
        assertEquals(dto.price(), domain.getPrice());
        assertEquals(dto.dineInOnly(), domain.isDineInOnly());
        assertEquals(dto.photoPath(), domain.getPhotoPath());
    }

    @Test
    @DisplayName("Deve mapear UpdateMenuItemInputDTO para MenuItem corretamente")
    void deveMapearUpdateMenuItemInputDTOParaMenuItem() {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                id,
                "Pizza Pepperoni",
                "Pizza sabor pepperoni",
                new BigDecimal("40.00"),
                restaurantId,
                false,
                "path/to/pepperoni.jpg"
        );

        MenuItem domain = mapper.toDomain(dto);

        assertEquals(dto.id(), domain.getId());
        assertEquals(dto.restaurantId(), domain.getRestaurantId());
        assertEquals(dto.name(), domain.getName());
        assertEquals(dto.description(), domain.getDescription());
        assertEquals(dto.price(), domain.getPrice());
        assertEquals(dto.dineInOnly(), domain.isDineInOnly());
        assertEquals(dto.photoPath(), domain.getPhotoPath());
    }

    @Test
    @DisplayName("Deve mapear MenuItem para CreateMenuItemOutputDTO corretamente")
    void deveMapearMenuItemParaCreateMenuItemOutputDTO() {
        MenuItem domain = criarMenuItemMock();

        CreateMenuItemOutputDTO dto = mapper.toCreateOutput(domain);

        assertEquals(domain.getId(), dto.id());
        assertEquals(domain.getName(), dto.name());
        assertEquals(domain.getDescription(), dto.description());
        assertEquals(domain.getPrice(), dto.price());
        assertEquals(domain.getRestaurantId(), dto.restaurantId());
        assertEquals(domain.isDineInOnly(), dto.dineInOnly());
        assertEquals(domain.getPhotoPath(), dto.photoPath());
    }

    @Test
    @DisplayName("Deve mapear MenuItem para GetMenuItemOutputDTO corretamente")
    void deveMapearMenuItemParaGetMenuItemOutputDTO() {
        MenuItem domain = criarMenuItemMock();

        GetMenuItemOutputDTO dto = mapper.toGetOutput(domain);

        assertEquals(domain.getId(), dto.id());
        assertEquals(domain.getName(), dto.name());
        assertEquals(domain.getDescription(), dto.description());
        assertEquals(domain.getPrice(), dto.price());
        assertEquals(domain.getRestaurantId(), dto.restaurantId());
        assertEquals(domain.isDineInOnly(), dto.dineInOnly());
        assertEquals(domain.getPhotoPath(), dto.photoPath());
    }

    @Test
    @DisplayName("Deve mapear lista de MenuItem para lista de GetMenuItemOutputDTO")
    void deveMapearListaDeMenuItemParaGetMenuItemOutputDTO() {
        MenuItem m1 = criarMenuItemMock();
        MenuItem m2 = criarMenuItemMock();

        List<GetMenuItemOutputDTO> lista = mapper.toGetOutputList(List.of(m1, m2));

        assertEquals(2, lista.size());
        assertEquals(m1.getId(), lista.get(0).id());
        assertEquals(m2.getId(), lista.get(1).id());
    }

    private MenuItem criarMenuItemMock() {
        return new MenuItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Nome Item",
                "Descrição item",
                new BigDecimal("20.00"),
                true,
                "path/to/photo.jpg"
        );
    }
}
