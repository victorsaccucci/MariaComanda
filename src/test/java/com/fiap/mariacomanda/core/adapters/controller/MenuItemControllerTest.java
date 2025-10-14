package com.fiap.mariacomanda.core.adapters.controller;

import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.usecases.menuItem.*;
import com.fiap.mariacomanda.core.dto.menuitem.input.*;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemControllerTest {

    private CreateMenuItemUseCase createMenuItemUseCase;
    private DeleteMenuItemUseCase deleteMenuItemUseCase;
    private GetMenuItemUseCase getMenuItemUseCase;
    private ListMenuItemUseCase listMenuItemUseCase;
    private UpdateMenuItemUseCase updateMenuItemUseCase;
    private MenuItemMapper menuItemMapper;

    private MenuItemController controller;

    private UUID menuItemId;
    private UUID restaurantId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        createMenuItemUseCase = mock(CreateMenuItemUseCase.class);
        deleteMenuItemUseCase = mock(DeleteMenuItemUseCase.class);
        getMenuItemUseCase = mock(GetMenuItemUseCase.class);
        listMenuItemUseCase = mock(ListMenuItemUseCase.class);
        updateMenuItemUseCase = mock(UpdateMenuItemUseCase.class);
        menuItemMapper = mock(MenuItemMapper.class);

        controller = new MenuItemController(
                createMenuItemUseCase,
                deleteMenuItemUseCase,
                getMenuItemUseCase,
                listMenuItemUseCase,
                updateMenuItemUseCase,
                menuItemMapper
        );

        menuItemId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar um menu item com sucesso")
    void testCreateMenuItem() {
        CreateMenuItemInputDTO inputDTO = mock(CreateMenuItemInputDTO.class);
        MenuItem menuItem = mock(MenuItem.class);
        CreateMenuItemOutputDTO expectedOutput = mock(CreateMenuItemOutputDTO.class);

        when(createMenuItemUseCase.execute(inputDTO, userId)).thenReturn(menuItem);
        when(menuItemMapper.toCreateOutput(menuItem)).thenReturn(expectedOutput);

        CreateMenuItemOutputDTO result = controller.create(inputDTO, userId);

        assertEquals(expectedOutput, result);
        verify(createMenuItemUseCase).execute(inputDTO, userId);
        verify(menuItemMapper).toCreateOutput(menuItem);
    }

    @Test
    @DisplayName("Deve retornar um menu item quando encontrado")
    void testGetMenuItem_withFound() {
        GetMenuItemInputDTO inputDTO = mock(GetMenuItemInputDTO.class);
        MenuItem menuItem = mock(MenuItem.class);
        GetMenuItemOutputDTO expected = mock(GetMenuItemOutputDTO.class);

        when(inputDTO.id()).thenReturn(menuItemId);
        when(getMenuItemUseCase.execute(menuItemId)).thenReturn(Optional.of(menuItem));
        when(menuItemMapper.toGetOutput(menuItem)).thenReturn(expected);

        GetMenuItemOutputDTO result = controller.get(inputDTO);

        assertEquals(expected, result);
        verify(getMenuItemUseCase).execute(menuItemId);
        verify(menuItemMapper).toGetOutput(menuItem);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando menu item não existe ao buscar por ID")
    void testGetMenuItem_notFound() {
        GetMenuItemInputDTO inputDTO = mock(GetMenuItemInputDTO.class);

        when(inputDTO.id()).thenReturn(menuItemId);
        when(getMenuItemUseCase.execute(menuItemId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> controller.get(inputDTO));

        assertTrue(exception.getMessage().toLowerCase().contains("menu item not found"));
        verify(getMenuItemUseCase).execute(menuItemId);
        verify(menuItemMapper, never()).toGetOutput(any());
    }

    @Test
    @DisplayName("Deve listar menu items por restaurante/paginação")
    void testListMenuItems() {
        ListMenuItemInputDTO inputDTO = mock(ListMenuItemInputDTO.class);
        List<MenuItem> menuItems = List.of(
                new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa", new BigDecimal("30.0"), false, "/path"),
                new MenuItem(UUID.randomUUID(), restaurantId, "Refrigerante", "Bebida gelada", new BigDecimal("8.0"), false, "/img")
        );
        List<GetMenuItemOutputDTO> expectedList = List.of(
                mock(GetMenuItemOutputDTO.class),
                mock(GetMenuItemOutputDTO.class)
        );

        when(inputDTO.restaurantId()).thenReturn(restaurantId);
        when(inputDTO.page()).thenReturn(0);
        when(inputDTO.size()).thenReturn(10);

        when(listMenuItemUseCase.execute(restaurantId, 0, 10)).thenReturn(menuItems);
        when(menuItemMapper.toGetOutputList(menuItems)).thenReturn(expectedList);

        List<GetMenuItemOutputDTO> result = controller.list(inputDTO);

        assertEquals(expectedList, result);
        verify(listMenuItemUseCase).execute(restaurantId, 0, 10);
        verify(menuItemMapper).toGetOutputList(menuItems);
    }

    @Test
    @DisplayName("Deve atualizar um menu item com sucesso")
    void testUpdateMenuItem() {
        UpdateMenuItemInputDTO inputDTO = mock(UpdateMenuItemInputDTO.class);
        MenuItem updated = mock(MenuItem.class);
        GetMenuItemOutputDTO outputDTO = mock(GetMenuItemOutputDTO.class);

        when(updateMenuItemUseCase.execute(inputDTO, userId)).thenReturn(updated);
        when(menuItemMapper.toGetOutput(updated)).thenReturn(outputDTO);

        GetMenuItemOutputDTO result = controller.update(inputDTO, userId);

        assertEquals(outputDTO, result);
        verify(updateMenuItemUseCase).execute(inputDTO, userId);
        verify(menuItemMapper).toGetOutput(updated);
    }

    @Test
    @DisplayName("Deve deletar um menu item com sucesso")
    void testDeleteMenuItem() {
        DeleteMenuItemInputDTO inputDTO = mock(DeleteMenuItemInputDTO.class);

        when(inputDTO.id()).thenReturn(menuItemId);

        controller.delete(inputDTO, userId);

        verify(deleteMenuItemUseCase, times(1)).execute(menuItemId, userId);
    }
}