package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteRestaurantUseCaseImplTest {

    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private DeleteRestaurantUseCaseImpl useCase;

    private UUID restaurantId;
    private UUID requesterId;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        useCase = new DeleteRestaurantUseCaseImpl(restaurantGateway, userGateway);

        restaurantId = UUID.randomUUID();
        requesterId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve deletar restaurante com sucesso quando requester for dono")
    void deveDeletarRestauranteComSucesso() {
        UserType type = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User requester = new User(requesterId, "teste", "fiap@test.com", "pass", type);
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante", "Endereço", "Italiana", "10:00-22:00", requesterId);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        assertDoesNotThrow(() -> useCase.execute(restaurantId, requesterId));

        verify(restaurantGateway, times(1)).deleteById(restaurantId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurantId for nulo")
    void deveLancarExcecaoQuandoRestaurantIdNulo() {
        assertThrows(ValidationException.class, () -> useCase.execute(null, requesterId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não existir")
    void deveLancarExcecaoQuandoRequesterNaoExistir() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(restaurantId, requesterId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não existir")
    void deveLancarExcecaoQuandoRestauranteNaoExistir() {
        UserType type = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", type);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(restaurantId, requesterId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for dono do restaurante")
    void deveLancarExcecaoQuandoRequesterNaoForDono() {
        UserType type = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", type);
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurante", "Endereço", "Italiana", "10:00-22:00", UUID.randomUUID());

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        assertThrows(ValidationException.class, () -> useCase.execute(restaurantId, requesterId));
    }
}
