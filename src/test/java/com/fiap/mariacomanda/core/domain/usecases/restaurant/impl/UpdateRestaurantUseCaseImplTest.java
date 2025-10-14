package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateRestaurantUseCaseImplTest {

    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private UpdateRestaurantUseCaseImpl useCase;

    private UUID restaurantId;
    private UUID requesterId;

    @BeforeEach
    void setUp() {
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        useCase = new UpdateRestaurantUseCaseImpl(restaurantGateway, userGateway);

        restaurantId = UUID.randomUUID();
        requesterId = UUID.randomUUID();
    }

    UserType type = new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER);

    @Test
    @DisplayName("Deve atualizar restaurante com sucesso mantendo o mesmo owner")
    void deveAtualizarRestauranteComSucesso() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", type);
        Restaurant existing = new Restaurant(restaurantId, "Nome Antigo", "Endereço", "Italiana", "10:00-22:00", requesterId);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(restaurantId, "Nome Novo", null, null, null, null);
        Restaurant result = useCase.execute(dto, requesterId);

        assertEquals("Nome Novo", result.getName());
        assertEquals(existing.getAddress(), result.getAddress());
        assertEquals(requesterId, result.getOwnerUserId());
        verify(restaurantGateway, times(1)).save(any(Restaurant.class));
    }

    @Test
    @DisplayName("Deve atualizar restaurante com transferência de owner")
    void deveAtualizarRestauranteComNovoOwner() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", type);
        UUID newOwnerId = UUID.randomUUID();
        User newOwner = new User(newOwnerId, "Novo Dono", "novo@test.com", "pass", type);
        Restaurant existing = new Restaurant(restaurantId, "Nome", "Endereço", "Italiana", "10:00-22:00", requesterId);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(newOwnerId)).thenReturn(Optional.of(newOwner));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existing));
        when(restaurantGateway.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(restaurantId, null, null, null, null, newOwnerId);
        Restaurant result = useCase.execute(dto, requesterId);

        assertEquals(newOwnerId, result.getOwnerUserId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inputDTO ou id forem nulos")
    void deveLancarExcecaoQuandoInputNulo() {
        assertThrows(ValidationException.class, () -> useCase.execute(null, requesterId));
        assertThrows(ValidationException.class, () -> useCase.execute(new UpdateRestaurantInputDTO(null, null, null, null, null, null), requesterId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não existir")
    void deveLancarExcecaoQuandoRequesterNaoExistir() {
        when(userGateway.findById(requesterId)).thenReturn(Optional.empty());
        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(restaurantId, "Nome", null, null, null, null);
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(dto, requesterId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando requester não for dono")
    void deveLancarExcecaoQuandoRequesterNaoForDono() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", type);
        Restaurant existing = new Restaurant(restaurantId, "Nome", "Endereço", "Italiana", "10:00-22:00", UUID.randomUUID());

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existing));

        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(restaurantId, "Nome", null, null, null, null);
        assertThrows(ValidationException.class, () -> useCase.execute(dto, requesterId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não existir")
    void deveLancarExcecaoQuandoRestauranteNaoExistir() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", type);
        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(restaurantId, "Nome", null, null, null, null);
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(dto, requesterId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando novo owner não existir")
    void deveLancarExcecaoQuandoNovoOwnerNaoExistir() {
        User requester = new User(requesterId, "Owner", "owner@test.com", "pass", type);
        UUID newOwnerId = UUID.randomUUID();
        Restaurant existing = new Restaurant(restaurantId, "Nome", "Endereço", "Italiana", "10:00-22:00", requesterId);

        when(userGateway.findById(requesterId)).thenReturn(Optional.of(requester));
        when(userGateway.findById(newOwnerId)).thenReturn(Optional.empty());
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existing));

        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(restaurantId, null, null, null, null, newOwnerId);
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(dto, requesterId));
    }
}
