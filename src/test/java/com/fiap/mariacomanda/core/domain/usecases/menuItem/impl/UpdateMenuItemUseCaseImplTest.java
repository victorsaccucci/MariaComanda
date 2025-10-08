package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
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
    private UpdateMenuItemUseCaseImpl updateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCaseImpl(menuItemGateway, restaurantGateway);
    }

    @Test
    @DisplayName("Deve atualizar MenuItem com sucesso")
    void execute_deveAtualizarMenuItemComSucesso() {
        UUID menuItemId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                menuItemId,
                "Pizza Premium",
                "Pizza com borda recheada",
                new BigDecimal("59.90"),
                restaurantId,
                true,
                "pizza-premium.png"
        );

        MenuItem existing = new MenuItem(
                menuItemId,
                restaurantId,
                "Pizza Simples",
                "Pizza comum",
                new BigDecimal("39.90"),
                false,
                "pizza-simples.png"
        );

        Restaurant restaurant = new Restaurant(
                restaurantId,
                "Restaurante Teste",
                "Rua A",
                "Italiana",
                "08:00-20:00",
                UUID.randomUUID()
        );

        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem result = updateMenuItemUseCase.execute(dto);

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.description(), result.getDescription());
        assertEquals(dto.price(), result.getPrice());
        assertEquals(dto.restaurantId(), result.getRestaurantId());
        assertEquals(dto.dineInOnly(), result.isDineInOnly());
        assertEquals(dto.photoPath(), result.getPhotoPath());

        verify(menuItemGateway).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando inputDTO for nulo")
    void execute_deveLancarExcecaoQuandoInputDTONulo() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> updateMenuItemUseCase.execute(null)
        );

        assertEquals("UpdateMenuItemInputDTO cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando id for nulo")
    void execute_deveLancarExcecaoQuandoIdForNulo() {
        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                null, "Pizza", "Desc", new BigDecimal("10.0"),
                UUID.randomUUID(), false, "foto.png"
        );

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> updateMenuItemUseCase.execute(dto)
        );

        assertEquals("MenuItem id is required", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando MenuItem não for encontrado")
    void execute_deveLancarExcecaoQuandoMenuItemNaoEncontrado() {
        UUID id = UUID.randomUUID();
        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                id, "Pizza", "Desc", new BigDecimal("10.0"),
                UUID.randomUUID(), false, "foto.png"
        );

        when(menuItemGateway.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> updateMenuItemUseCase.execute(dto)
        );

        assertEquals("MenuItem not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve manter o restaurantId existente quando o DTO tiver restaurantId nulo")
    void execute_deveManterRestaurantIdQuandoDtoTiverNulo() {
        UUID menuItemId = UUID.randomUUID();
        UUID existingRestaurantId = UUID.randomUUID();

        // DTO sem restaurantId
        UpdateMenuItemInputDTO dto = new UpdateMenuItemInputDTO(
                menuItemId,
                "Pizza Nova",
                "Pizza atualizada",
                new BigDecimal("45.00"),
                null, // restaurantId é null
                true,
                "pizza-nova.png"
        );

        MenuItem existing = new MenuItem(
                menuItemId,
                existingRestaurantId,
                "Pizza Antiga",
                "Pizza antiga descrição",
                new BigDecimal("30.00"),
                false,
                "pizza-antiga.png"
        );

        Restaurant existingRestaurant = new Restaurant(
                existingRestaurantId,
                "Restaurante Existente",
                "Rua A",
                "Italiana",
                "08:00-20:00",
                UUID.randomUUID()
        );

        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(existingRestaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(menuItemGateway.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem result = updateMenuItemUseCase.execute(dto);

        assertNotNull(result);
        assertEquals(existingRestaurantId, result.getRestaurantId(), "Deve manter o restaurantId original");
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.description(), result.getDescription());
        assertEquals(dto.price(), result.getPrice());
        assertEquals(dto.photoPath(), result.getPhotoPath());
        assertTrue(result.isDineInOnly());

        verify(restaurantGateway).findById(existingRestaurantId);
        verify(menuItemGateway).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("resolveRestaurant deve lançar exceção quando restaurantId for nulo")
    void resolveRestaurant_deveLancarExcecaoQuandoIdForNulo() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> updateMenuItemUseCase.resolveRestaurant(null)
        );

        assertEquals("Restaurant ID cannot be null", ex.getMessage());
    }

    // 🔹 2. Deve lançar exceção quando restaurante não for encontrado
    @Test
    @DisplayName("resolveRestaurant deve lançar exceção quando restaurante não for encontrado")
    void resolveRestaurant_deveLancarExcecaoQuandoNaoEncontrarRestaurante() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> updateMenuItemUseCase.resolveRestaurant(restaurantId)
        );

        assertEquals("Restaurant not found", ex.getMessage());
        verify(restaurantGateway).findById(restaurantId);
    }

    // 🔹 3. Deve retornar restaurante quando encontrado
    @Test
    @DisplayName("resolveRestaurant deve retornar restaurante quando encontrado")
    void resolveRestaurant_deveRetornarRestauranteQuandoEncontrado() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                restaurantId,
                "Restaurante Bom",
                "Rua A",
                "Italiana",
                "08:00-20:00",
                UUID.randomUUID()
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        Restaurant result = updateMenuItemUseCase.resolveRestaurant(restaurantId);

        assertNotNull(result);
        assertEquals(restaurant, result);
        verify(restaurantGateway).findById(restaurantId);
    }

    @Test
    @DisplayName("updateValue(String) deve retornar o novo valor quando não for nulo")
    void updateValueString_deveRetornarNovoValorQuandoNaoForNulo() {
        String result = updateMenuItemUseCase.updateValue("Novo", "Antigo");
        assertEquals("Novo", result);
    }

    @Test
    @DisplayName("updateValue(String) deve retornar o valor atual quando novo for nulo")
    void updateValueString_deveRetornarValorAtualQuandoNovoForNulo() {
        String result = updateMenuItemUseCase.updateValue(null, "Antigo");
        assertEquals("Antigo", result);
    }

    @Test
    @DisplayName("updateValue(BigDecimal) deve retornar o novo valor quando não for nulo")
    void updateValueBigDecimal_deveRetornarNovoValorQuandoNaoForNulo() {
        BigDecimal result = updateMenuItemUseCase.updateValue(
                new BigDecimal("15.50"),
                new BigDecimal("10.00")
        );
        assertEquals(new BigDecimal("15.50"), result);
    }

    @Test
    @DisplayName("updateValue(BigDecimal) deve retornar o valor atual quando novo for nulo")
    void updateValueBigDecimal_deveRetornarValorAtualQuandoNovoForNulo() {
        BigDecimal result = updateMenuItemUseCase.updateValue(
                null,
                new BigDecimal("10.00")
        );
        assertEquals(new BigDecimal("10.00"), result);
    }

}
