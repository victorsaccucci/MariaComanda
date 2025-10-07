package com.fiap.mariacomanda.core.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes da entidade Restaurant")
class RestaurantTest {

    @Test
    @DisplayName("Deve criar restaurante válido com todos os campos obrigatórios")
    void deveCriarRestauranteValido() {
        UUID id = UUID.randomUUID();
        UUID ownerUserId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(
                id,
                "Pizzaria da Vila",
                "Rua XPTO, 123",
                "Italiana",
                "18:00-23:00",
                ownerUserId
        );
        assertEquals("Pizzaria da Vila", restaurant.getName());
        assertEquals("Rua XPTO, 123", restaurant.getAddress());
        assertEquals("Italiana", restaurant.getCuisineType());
        assertEquals("18:00-23:00", restaurant.getOpeningHours());
        assertEquals(ownerUserId, restaurant.getOwnerUserId());
        assertEquals(id, restaurant.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção se o nome for nulo")
    void deveLancarExcecaoQuandoNomeForNulo() {
        UUID ownerUserId = UUID.randomUUID();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant(null, "Rua XPTO, 123", "Italiana", "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant name cannot be null or empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o nome for vazio")
    void deveLancarExcecaoQuandoNomeForVazio() {
        UUID ownerUserId = UUID.randomUUID();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("  ", "Rua XPTO, 123", "Italiana", "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant name cannot be null or empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o nome exceder 120 caracteres")
    void deveLancarExcecaoQuandoNomeExcederLimite() {
        UUID ownerUserId = UUID.randomUUID();
        String longName = "A".repeat(121);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant(longName, "Rua XPTO, 123", "Italiana", "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant name cannot exceed 120 characters", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o endereço for nulo")
    void deveLancarExcecaoQuandoEnderecoForNulo() {
        UUID ownerUserId = UUID.randomUUID();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("Pizzaria", null, "Italiana", "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant address cannot be null or empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o endereço for vazio")
    void deveLancarExcecaoQuandoEnderecoForVazio() {
        UUID ownerUserId = UUID.randomUUID();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("Pizzaria", "   ", "Italiana", "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant address cannot be null or empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o endereço exceder 255 caracteres")
    void deveLancarExcecaoQuandoEnderecoExcederLimite() {
        UUID ownerUserId = UUID.randomUUID();
        String longAddress = "X".repeat(256);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("Pizzaria", longAddress, "Italiana", "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant address cannot exceed 255 characters", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o tipo de culinária for nulo")
    void deveLancarExcecaoQuandoTipoCulinariaForNulo() {
        UUID ownerUserId = UUID.randomUUID();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("Pizzaria", "Rua Y", null, "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant cuisine type cannot be null or empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o tipo de culinária for vazio")
    void deveLancarExcecaoQuandoTipoCulinariaForVazio() {
        UUID ownerUserId = UUID.randomUUID();
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("Pizzaria", "Rua Y", "   ", "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant cuisine type cannot be null or empty", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o tipo de culinária exceder 50 caracteres")
    void deveLancarExcecaoQuandoTipoCulinariaExcederLimite() {
        UUID ownerUserId = UUID.randomUUID();
        String longCuisine = "B".repeat(51);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("Pizzaria", "Rua Y", longCuisine, "18:00-23:00", ownerUserId)
        );
        assertEquals("Restaurant cuisine type cannot exceed 50 characters", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se o ownerUserId for nulo")
    void deveLancarExcecaoQuandoOwnerUserIdForNulo() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("Pizzaria", "Rua Y", "Italiana", "18:00-23:00", null)
        );
        assertEquals("Owner User ID cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar os campos com setters válidos")
    void deveAtualizarCamposComSettersValidos() {
        UUID ownerUserId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant("Nome", "Endereco", "Tipo", "Horário", ownerUserId);

        restaurant.setName("Novo Nome");
        restaurant.setAddress("Novo Endereço");
        restaurant.setCuisineType("Brasileira");
        UUID novoOwnerId = UUID.randomUUID();
        restaurant.setOwnerUserId(novoOwnerId);

        assertEquals("Novo Nome", restaurant.getName());
        assertEquals("Novo Endereço", restaurant.getAddress());
        assertEquals("Brasileira", restaurant.getCuisineType());
        assertEquals(novoOwnerId, restaurant.getOwnerUserId());
    }
}