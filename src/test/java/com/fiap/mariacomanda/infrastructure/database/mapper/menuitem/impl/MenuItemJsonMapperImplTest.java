package com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.impl;

import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.impl.MenuItemJsonMapperImpl;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateMenuItemJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemJsonMapperImplTest {

    private MenuItemJsonMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new MenuItemJsonMapperImpl();
    }

    @Test
    void deveMapearCreateMenuItemJsonParaCreateMenuItemInputDTO() throws Exception {
        CreateMenuItemJson json = new CreateMenuItemJson();

        setPrivateField(json, "name", "Pizza");
        setPrivateField(json, "description", "Pizza saborosa");
        setPrivateField(json, "price", BigDecimal.valueOf(29.99));
        setPrivateField(json, "restaurantId", UUID.randomUUID());
        setPrivateField(json, "dineInOnly", true);
        setPrivateField(json, "photoPath", "/images/pizza.jpg");

        CreateMenuItemInputDTO dto = mapper.toCreateInput(json);

        assertEquals("Pizza", dto.name());
        assertEquals("Pizza saborosa", dto.description());
        assertEquals(BigDecimal.valueOf(29.99), dto.price());
        assertNotNull(dto.restaurantId());
        assertTrue(dto.dineInOnly());
        assertEquals("/images/pizza.jpg", dto.photoPath());
    }

    
    @Test
    void deveMapearUpdateMenuItemJsonParaUpdateMenuItemInputDTO() throws Exception {
        UpdateMenuItemJson json = new UpdateMenuItemJson();
        UUID id = UUID.randomUUID();

        setPrivateField(json, "name", "Lasanha");
        setPrivateField(json, "description", "Lasanha deliciosa");
        setPrivateField(json, "price", BigDecimal.valueOf(35.50));
        setPrivateField(json, "restaurantId", UUID.randomUUID());
        setPrivateField(json, "dineInOnly", false);
        setPrivateField(json, "photoPath", "/images/lasanha.jpg");

        UpdateMenuItemInputDTO dto = mapper.toUpdateInput(id, json);

        assertEquals(id, dto.id());
        assertEquals("Lasanha", dto.name());
        assertEquals("Lasanha deliciosa", dto.description());
        assertEquals(BigDecimal.valueOf(35.50), dto.price());
        assertNotNull(dto.restaurantId());
        assertFalse(dto.dineInOnly());
        assertEquals("/images/lasanha.jpg", dto.photoPath());
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
