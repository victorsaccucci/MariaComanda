package com.fiap.mariacomanda.core.domain.usecases.menuItem.impl;

import com.fiap.mariacomanda.core.adapters.gateway.MenuItemGateway;
import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.MenuItem;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.EntityNotFoundException;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteMenuItemUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private DeleteMenuItemUseCaseImpl useCase;

    private UUID menuItemId;
    private UUID requesterUserId;
    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        useCase = new DeleteMenuItemUseCaseImpl(menuItemGateway, restaurantGateway, userGateway);

        menuItemId = UUID.randomUUID();
        requesterUserId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
    }

    UserType type = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);

    @Test
    @DisplayName("Deve deletar menu item com sucesso")
    void deveDeletarMenuItemComSucesso() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", type);
        MenuItem existing = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa", new BigDecimal("45.0"), true, "/images/pizza.jpg");
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante X", "Rua Y", "Italiana", "10:00-22:00", requesterUserId);

        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        useCase.execute(menuItemId, requesterUserId);

        verify(menuItemGateway).deleteById(menuItemId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando menuItemId for nulo")
    void deveLancarExcecaoQuandoMenuItemIdNulo() {
        assertThrows(ValidationException.class,
                () -> useCase.execute(null, requesterUserId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não existir")
    void deveLancarExcecaoQuandoRequesterNaoExistir() {
        when(userGateway.findById(requesterUserId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(menuItemId, requesterUserId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando menuItem não existir")
    void deveLancarExcecaoQuandoMenuItemNaoExistir() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", type);
        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(menuItemId, requesterUserId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não existir")
    void deveLancarExcecaoQuandoRestauranteNaoExistir() {
        User requester = new User(requesterUserId, "Dono", "dono@email.com", "senha", type);
        MenuItem existing = new MenuItem(menuItemId, restaurantId, "Pizza", "Deliciosa", new BigDecimal("45.0"), true, "/images/pizza.jpg");

        when(userGateway.findById(requesterUserId)).thenReturn(Optional.of(requester));
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> useCase.execute(menuItemId, requesterUserId));
    }
}
