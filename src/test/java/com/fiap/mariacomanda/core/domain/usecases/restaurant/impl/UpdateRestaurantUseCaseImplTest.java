package com.fiap.mariacomanda.core.domain.usecases.restaurant.impl;

import com.fiap.mariacomanda.core.adapters.gateway.RestaurantGateway;
import com.fiap.mariacomanda.core.adapters.gateway.UserGateway;
import com.fiap.mariacomanda.core.domain.entity.Restaurant;
import com.fiap.mariacomanda.core.domain.entity.User;
import com.fiap.mariacomanda.core.domain.entity.UserType;
import com.fiap.mariacomanda.core.dto.restaurant.input.UpdateRestaurantInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateRestaurantUseCaseImplTest {

    private RestaurantGateway restaurantGateway;
    private UserGateway userGateway;
    private UpdateRestaurantUseCaseImpl updateRestaurantUseCase;

    @BeforeEach
    void setup() {
        restaurantGateway = mock(RestaurantGateway.class);
        userGateway = mock(UserGateway.class);
        updateRestaurantUseCase = new UpdateRestaurantUseCaseImpl(restaurantGateway, userGateway);
    }

    @Test
    @DisplayName("Deve atualizar restaurante usando ownerUserId existente quando inputDTO.ownerUserId for nulo")
    void execute_deveAtualizarRestauranteUsandoOwnerExistente() {
        UUID restaurantId = UUID.randomUUID();
        UUID existingOwnerId = UUID.randomUUID();

        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(
                restaurantId,
                "NovoNome",
                "NovoEndereço",
                "NovaCulinária",
                "08:00-20:00",
                null // ownerUserId nulo
        );

        Restaurant existing = new Restaurant(
                restaurantId,
                "OldNome",
                "OldEndereço",
                "OldCulinária",
                "07:00-18:00",
                existingOwnerId
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existing));
        when(userGateway.findById(existingOwnerId)).thenReturn(Optional.of(
                new User("Nome", "email@teste.com", "senhaHash",
                        new UserType(UUID.randomUUID(), "OWNER", UserType.OWNER))
        ));
        when(restaurantGateway.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant result = updateRestaurantUseCase.execute(dto);

        verify(restaurantGateway).save(any(Restaurant.class));

        assertNotNull(result);
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.address(), result.getAddress());
        assertEquals(dto.cuisineType(), result.getCuisineType());
        assertEquals(dto.openingHours(), result.getOpeningHours());
        assertEquals(existingOwnerId, result.getOwnerUserId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ownerType for null")
    void execute_deveLancarExcecaoQuandoOwnerTypeForNull() {
        UUID restaurantId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(
                restaurantId,
                "NovoNome",
                "NovoEndereço",
                "NovaCulinária",
                "08:00-20:00",
                ownerId
        );

        Restaurant existing = new Restaurant(
                restaurantId,
                "OldNome",
                "OldEndereço",
                "OldCulinária",
                "07:00-18:00",
                UUID.randomUUID()
        );

        User mockUser = mock(User.class);
        when(mockUser.getUserType()).thenReturn(null);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existing));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(mockUser));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> updateRestaurantUseCase.execute(dto)
        );

        assertEquals("Only OWNER users can own restaurants", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inputDTO for nulo")
    void execute_deveLancarExcecaoQuandoInputDTONulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> updateRestaurantUseCase.execute(null)
        );

        assertEquals("UpdateRestaurantInputDTO cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando id do inputDTO for nulo")
    void execute_deveLancarExcecaoQuandoIdNulo() {
        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(
                null, "Nome", "Endereco", "Culinaria", "08:00-20:00", UUID.randomUUID()
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> updateRestaurantUseCase.execute(dto)
        );

        assertEquals("Restaurant id is required", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não existir")
    void execute_deveLancarExcecaoQuandoRestauranteNaoExistir() {
        UUID restaurantId = UUID.randomUUID();
        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(
                restaurantId, "Nome", "Endereco", "Culinaria", "08:00-20:00", UUID.randomUUID()
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> updateRestaurantUseCase.execute(dto)
        );

        assertEquals("Restaurant not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ownerUserId não for OWNER")
    void execute_deveLancarExcecaoQuandoOwnerNaoForOWNER() {
        UUID restaurantId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        UpdateRestaurantInputDTO dto = new UpdateRestaurantInputDTO(
                restaurantId, "Nome", "Endereco", "Culinaria", "08:00-20:00", ownerId
        );

        Restaurant existing = new Restaurant(
                restaurantId, "OldNome", "OldEndereco", "OldCulinaria", "07:00-18:00", UUID.randomUUID()
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existing));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(
                new User("Nome", "email@teste.com", "senhaHash",
                        new UserType(UUID.randomUUID(), "CUSTOMER", UserType.CUSTOMER))
        ));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> updateRestaurantUseCase.execute(dto)
        );

        assertEquals("Only OWNER users can own restaurants", exception.getMessage());
    }

    @Test
    @DisplayName("resolveUser deve lançar exceção quando userId for null")
    void resolveUser_deveLancarExcecaoQuandoUserIdForNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> updateRestaurantUseCase.resolveUser(null)
        );

        assertEquals("User ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("resolveUser deve lançar exceção quando usuário não existir")
    void resolveUser_deveLancarExcecaoQuandoUsuarioNaoExistir() {
        UUID userId = UUID.randomUUID();

        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> updateRestaurantUseCase.resolveUser(userId)
        );

        assertEquals("User not found for id: " + userId, exception.getMessage());
    }

    @Test
    @DisplayName("updateValue deve retornar novo valor quando não for nulo")
    void updateValue_deveRetornarNovoValorQuandoNaoForNulo() {
        String novoValor = "NovoValor";
        String valorAtual = "ValorAtual";

        String resultado = updateRestaurantUseCase.updateValue(novoValor, valorAtual);

        assertEquals(novoValor, resultado);
    }

    @Test
    @DisplayName("updateValue deve retornar valor atual quando novo valor for nulo")
    void updateValue_deveRetornarValorAtualQuandoNovoValorForNulo() {
        String novoValor = null;
        String valorAtual = "ValorAtual";

        String resultado = updateRestaurantUseCase.updateValue(novoValor, valorAtual);

        assertEquals(valorAtual, resultado);
    }
}
