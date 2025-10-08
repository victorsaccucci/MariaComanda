package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.menuitem.input.CreateMenuItemInputDTO;
import com.fiap.mariacomanda.core.mapper.MenuItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateMenuItemUseCaseImplTest {

    private CreateMenuItemUseCaseImpl createMenuItemUseCase;
    private MenuItemGateway menuItemGateway;
    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private MenuItemMapper menuItemMapper;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        menuItemMapper = mock(MenuItemMapper.class);

        createMenuItemUseCase = new CreateMenuItemUseCaseImpl(
                menuItemGateway, restaurantGateway, userGateway, menuItemMapper
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando inputDTO for null")
    void execute_deveLancarExcecaoQuandoInputDTOForNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> createMenuItemUseCase.execute(null, UUID.randomUUID())
        );

        assertEquals("CreateMenuItemInputDTO cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requesterType for null")
    void execute_deveLancarExcecaoQuandoRequesterTypeForNull() {
        UUID requesterId = UUID.randomUUID();
        CreateMenuItemInputDTO dto = mock(CreateMenuItemInputDTO.class);

        User requesterMock = mock(User.class);
        when(requesterMock.getUserType()).thenReturn(null);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requesterMock));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> createMenuItemUseCase.execute(dto, requesterId)
        );

        assertEquals("Only OWNER users can create menu items", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for OWNER")
    void execute_deveLancarExcecaoQuandoRequesterNaoForOwner() {
        UUID requesterId = UUID.randomUUID();
        CreateMenuItemInputDTO dto = mock(CreateMenuItemInputDTO.class);

        UserType type = new UserType(UUID.randomUUID(), "CUSTOMER", UserType.CUSTOMER);
        User requester = new User("Nome", "email@teste.com", "senhaHash", type);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> createMenuItemUseCase.execute(dto, requesterId)
        );

        assertEquals("Only OWNER users can create menu items", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for dono do restaurante")
    void execute_deveLancarExcecaoQuandoRequesterNaoForDonoDoRestaurante() {
        UUID requesterId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        CreateMenuItemInputDTO dto = mock(CreateMenuItemInputDTO.class);

        when(dto.restaurantId()).thenReturn(restaurantId);

        UserType type = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User requester = new User("Nome", "email@teste.com", "senhaHash", type);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(
                new Restaurant(UUID.randomUUID(), "Restaurante", "Endereço", "Culinária", "08:00-20:00", UUID.randomUUID())
        ));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> createMenuItemUseCase.execute(dto, requesterId)
        );

        assertEquals("Requester does not own the restaurant", ex.getMessage());
    }

    @Test
    @DisplayName("Deve criar MenuItem com sucesso")
    void execute_deveCriarMenuItemComSucesso() {
        UUID requesterId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        CreateMenuItemInputDTO dto = mock(CreateMenuItemInputDTO.class);

        when(dto.restaurantId()).thenReturn(restaurantId);

        UserType type = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User requester = new User("Nome", "email@teste.com", "senhaHash", type);

        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante", "Endereço", "Culinária", "08:00-20:00", requesterId);

        MenuItem menuItem = new MenuItem(
                restaurantId,               // UUID restaurantId
                "Item",                     // String name
                "Descrição",                // String description
                BigDecimal.valueOf(10.0),  // BigDecimal price
                false,                      // boolean dineInOnly
                null                        // String photoPath (pode ser null)
        );

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemMapper.toDomain(dto)).thenReturn(menuItem);
        when(menuItemGateway.save(menuItem)).thenReturn(menuItem);

        MenuItem resultado = createMenuItemUseCase.execute(dto, requesterId);

        assertNotNull(resultado);
        assertEquals(menuItem, resultado);

        verify(menuItemGateway).save(menuItem);
    }

    @Test
    @DisplayName("Deve lançar exceção quando requesterUserId for null")
    void resolveRequester_deveLancarExcecaoQuandoRequesterUserIdForNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> createMenuItemUseCase.resolveRequester(null)
        );

        assertEquals("Requester user ID cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void resolveRequester_deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        UUID requesterId = UUID.randomUUID();
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> createMenuItemUseCase.resolveRequester(requesterId)
        );

        assertEquals("Requester user not found", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurantId for null")
    void resolveRestaurant_deveLancarExcecaoQuandoRestaurantIdForNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> createMenuItemUseCase.resolveRestaurant(null)
        );

        assertEquals("Restaurant ID cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não for encontrado")
    void resolveRestaurant_deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> createMenuItemUseCase.resolveRestaurant(restaurantId)
        );

        assertEquals("Restaurant not found", ex.getMessage());
    }
}
