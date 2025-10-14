package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.dto.menuitem.input.UpdateMenuItemInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateMenuItemUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private UpdateMenuItemUseCaseImpl useCase;

    private UUID menuItemId;
    private UUID restaurantId;
    private UUID requesterId;

    private UserType ownerType;
    private User requester;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        useCase = new UpdateMenuItemUseCaseImpl(menuItemGateway, restaurantGateway, userGateway);

        menuItemId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
        requesterId = UUID.randomUUID();

        ownerType = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        requester = new User(requesterId, "Owner", "owner@test.com", "pass", ownerType);
    }

    @Test
    @DisplayName("Deve atualizar todos os campos do menu item com sucesso")
    void deveAtualizarTodosCampos() {
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante", "Endereço", "Italiana", "10:00-22:00", requesterId);
        MenuItem existing = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa",
                new BigDecimal("45"), true, "/images/pizza.jpg");

        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                menuItemId, "Pizza Pepperoni", "Muito deliciosa",
                new BigDecimal("50"), restaurantId, false, "/images/pepperoni.jpg"
        );

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem updated = useCase.execute(dto, requesterId);

        assertEquals("Pizza Pepperoni", updated.getName());
        assertEquals("Muito deliciosa", updated.getDescription());
        assertEquals(new BigDecimal("50"), updated.getPrice());
        assertFalse(updated.isDineInOnly());
        assertEquals("/images/pepperoni.jpg", updated.getPhotoPath());
    }

    @Test
    @DisplayName("Deve atualizar apenas campos fornecidos e manter os demais")
    void deveAtualizarCamposParciais() {
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante", "Endereço", "Italiana", "10:00-22:00", requesterId);
        MenuItem existing = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa",
                new BigDecimal("45"), true, "/images/pizza.jpg");

        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                menuItemId, null, null, null, restaurantId, true, null
        );

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem updated = useCase.execute(dto, requesterId);

        assertEquals("Pizza", updated.getName());
        assertEquals("Deliciosa", updated.getDescription());
        assertEquals(new BigDecimal("45"), updated.getPrice());
        assertTrue(updated.isDineInOnly());
        assertEquals("/images/pizza.jpg", updated.getPhotoPath());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for encontrado")
    void deveLancarExcecaoRequesterNaoEncontrado() {
        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(menuItemId, "Pizza", "Desc", new BigDecimal("10"), restaurantId, true, "/images/test.jpg");
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(dto, requesterId));

        assertEquals("Requester user not found not found with identifier: " + requesterId, ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando MenuItem não encontrado")
    void deveLancarExcecaoMenuItemNaoEncontrado() {
        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(menuItemId, "Pizza", "Desc", new BigDecimal("10"), restaurantId, true, "/images/test.jpg");
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(dto, requesterId));

        assertEquals("MenuItem not found not found with identifier: " + menuItemId, ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não encontrado")
    void deveLancarExcecaoRestauranteNaoEncontrado() {
        MenuItem existing = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa",
                new BigDecimal("45"), true, "/images/pizza.jpg");

        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(menuItemId, "Pizza", "Desc", new BigDecimal("10"), restaurantId, true, "/images/test.jpg");

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(dto, requesterId));

        assertEquals("Restaurant not found for menu item not found with identifier: " + restaurantId, ex.getMessage());
    }

    @Test
    @DisplayName("Deve manter restaurantId existente quando inputDTO.restaurantId for nulo")
    void deveManterRestaurantIdExistenteQuandoDtoNulo() {
        MenuItem existing = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa",
                new BigDecimal("45"), true, "/images/pizza.jpg");

        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                menuItemId, "Pizza", "Desc", new BigDecimal("50"), null, true, "/images/test.jpg"
        );

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(new Restaurant(
                restaurantId, "Restaurante", "Endereço", "Italiana", "10:00-22:00", requesterId
        )));
        when(menuItemGateway.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem updated = useCase.execute(dto, requesterId);

        assertEquals(existing.getRestaurantId(), updated.getRestaurantId());
    }

    @Test
    @DisplayName("Deve atualizar restaurantId quando inputDTO.restaurantId não for nulo")
    void deveAtualizarRestaurantIdQuandoDtoNaoNulo() {
        UUID novoRestaurantId = UUID.randomUUID();

        MenuItem existing = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa",
                new BigDecimal("45"), true, "/images/pizza.jpg");

        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                menuItemId, "Pizza", "Desc", new BigDecimal("50"), novoRestaurantId, true, "/images/test.jpg"
        );

        Restaurant novoRestaurant = new Restaurant(novoRestaurantId, "Novo Restaurante", "Rua X", "Italiana", "10:00-22:00", requesterId);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(existing.getRestaurantId())).thenReturn(Optional.of(new Restaurant(
                existing.getRestaurantId(), "Restaurante", "Endereço", "Italiana", "10:00-22:00", requesterId
        )));
        when(restaurantGateway.findById(novoRestaurantId)).thenReturn(Optional.of(novoRestaurant));
        when(menuItemGateway.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem updated = useCase.execute(dto, requesterId);

        assertEquals(novoRestaurantId, updated.getRestaurantId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando novo restaurantId do DTO não for encontrado")
    void deveLancarExcecaoRestaurantNotFound() {
        UUID novoRestaurantId = UUID.randomUUID();

        MenuItem existing = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa",
                new BigDecimal("45"), true, "/images/pizza.jpg");

        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                menuItemId, "Pizza", "Deliciosa", new BigDecimal("50"), novoRestaurantId, true, "/images/test.jpg"
        );

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(new Restaurant(
                restaurantId, "Restaurante Antigo", "Endereço", "Italiana", "10:00-22:00", requesterId
        )));

        when(restaurantGateway.findById(novoRestaurantId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(dto, requesterId));

        assertEquals("Restaurant not found not found with identifier: " + novoRestaurantId, ex.getMessage());
    }


    @Test
    @DisplayName("updateValue(Boolean) deve retornar novo valor quando não for nulo")
    void updateValueBoolean_deveRetornarNovoValorQuandoNaoForNulo() {
        Boolean result = useCase.updateValue(false, true);
        assertFalse(result);
    }

    @Test
    @DisplayName("updateValue(Boolean) deve retornar valor atual quando novo for nulo")
    void updateValueBoolean_deveRetornarValorAtualQuandoNovoForNulo() {
        Boolean result = useCase.updateValue(null, true);
        assertTrue(result);
    }

}
