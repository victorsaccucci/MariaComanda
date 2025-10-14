package com.fiap.mariacomanda.infrastructure.web.controller;

import com.fiap.mariacomanda.core.adapters.controller.MenuItemController;
import com.fiap.mariacomanda.core.dto.menuitem.input.*;
import com.fiap.mariacomanda.core.dto.menuitem.output.CreateMenuItemOutputDTO;
import com.fiap.mariacomanda.core.dto.menuitem.output.GetMenuItemOutputDTO;
import com.fiap.mariacomanda.infrastructure.database.mapper.menuitem.MenuItemJsonMapper;
import com.fiap.mariacomanda.infrastructure.web.json.CreateMenuItemJson;
import com.fiap.mariacomanda.infrastructure.web.json.UpdateMenuItemJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuItemApiControllerTest {

    @Mock
    private MenuItemController menuItemController;

    @Mock
    private MenuItemJsonMapper menuItemJsonMapper;

    @InjectMocks
    private MenuItemApiController menuItemApiController;

    private UUID requesterUserId;
    private UUID menuItemId;
    private final BigDecimal price = BigDecimal.valueOf(25.50);

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        requesterUserId = UUID.randomUUID();
        menuItemId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve criar um item do menu com sucesso")
    void deveCriarMenuItemComSucesso() {
        CreateMenuItemJson json = new CreateMenuItemJson();
        CreateMenuItemInputDTO inputDTO = new CreateMenuItemInputDTO("Pizza", "Deliciosa pizza", price, UUID.randomUUID(), true, "pizza-premium.png");
        CreateMenuItemOutputDTO outputDTO = new CreateMenuItemOutputDTO(menuItemId);

        when(menuItemJsonMapper.toCreateInput(json)).thenReturn(inputDTO);
        when(menuItemController.create(inputDTO, requesterUserId)).thenReturn(outputDTO);

        CreateMenuItemOutputDTO result = menuItemApiController.create(requesterUserId, json);

        assertNotNull(result);
        assertEquals(menuItemId, result.id());
        verify(menuItemController).create(inputDTO, requesterUserId);
    }

    @Test
    @DisplayName("Deve retornar um item do menu pelo ID")
    void deveBuscarMenuItemPorId() {
        GetMenuItemOutputDTO outputDTO = new GetMenuItemOutputDTO(menuItemId, "Lasanha", "Feita na hora", price, UUID.randomUUID(), true, "lasanha-feita-na-hora.png");

        when(menuItemController.get(any(GetMenuItemInputDTO.class))).thenReturn(outputDTO);

        GetMenuItemOutputDTO result = menuItemApiController.get(menuItemId);

        assertNotNull(result);
        assertEquals("Lasanha", result.name());
        verify(menuItemController).get(any(GetMenuItemInputDTO.class));
    }

    @Test
    @DisplayName("Deve listar itens do menu com paginação")
    void deveListarMenuItemsComPaginacao() {
        UUID restaurantId = UUID.randomUUID();
        GetMenuItemOutputDTO item1 = new GetMenuItemOutputDTO(UUID.randomUUID(), "Suco", "Natural", price, restaurantId, true, "suco-laranja.png");
        GetMenuItemOutputDTO item2 = new GetMenuItemOutputDTO(UUID.randomUUID(), "Refrigerante", "Lata", price, restaurantId, true, "coca-cola.png");

        when(menuItemController.list(any(ListMenuItemInputDTO.class))).thenReturn(List.of(item1, item2));

        List<GetMenuItemOutputDTO> result = menuItemApiController.list(restaurantId, 0, 10);

        assertEquals(2, result.size());
        verify(menuItemController).list(any(ListMenuItemInputDTO.class));
    }

    @Test
    @DisplayName("Deve atualizar um item do menu com sucesso")
    void deveAtualizarMenuItemComSucesso() {
        UpdateMenuItemJson json = new UpdateMenuItemJson();
        UpdateMenuItemInputDTO inputDTO = new UpdateMenuItemInputDTO(menuItemId, "Hambúrguer", "Caseiro", price, UUID.randomUUID(), true, "hamburguer-caseiro.png");
        GetMenuItemOutputDTO outputDTO = new GetMenuItemOutputDTO(menuItemId, "Hambúrguer", "Caseiro", price, UUID.randomUUID(), true, "hamburguer.png");

        when(menuItemJsonMapper.toUpdateInput(menuItemId, json)).thenReturn(inputDTO);
        when(menuItemController.update(inputDTO, requesterUserId)).thenReturn(outputDTO);

        GetMenuItemOutputDTO result = menuItemApiController.update(requesterUserId, menuItemId, json);

        assertEquals("Hambúrguer", result.name());
        verify(menuItemController).update(inputDTO, requesterUserId);
    }

    @Test
    @DisplayName("Deve excluir um item do menu com sucesso")
    void deveExcluirMenuItemComSucesso() {
        ResponseEntity<Void> response = menuItemApiController.delete(requesterUserId, menuItemId);

        assertEquals(204, response.getStatusCodeValue());
        verify(menuItemController).delete(any(DeleteMenuItemInputDTO.class), eq(requesterUserId));
    }
}
