package com.fiap.mariacomanda.core.domain.entity;

import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private static final String VALID_NAME = "Restaurante Bom Sabor";
    private static final String VALID_ADDRESS = "Rua das Flores, 123";
    private static final String VALID_CUISINE_TYPE = "Italiana";
    private static final String VALID_OPENING_HOURS = "08:00-22:00";
    private static final UUID VALID_OWNER_ID = UUID.randomUUID();

    @Test
    @DisplayName("Construtor com ID deve criar objeto válido")
    void construtorComId_deveCriarObjetoValido() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(id, VALID_NAME, VALID_ADDRESS, VALID_CUISINE_TYPE,
                VALID_OPENING_HOURS, VALID_OWNER_ID);

        assertEquals(id, restaurant.getId());
        assertEquals(VALID_NAME, restaurant.getName());
        assertEquals(VALID_ADDRESS, restaurant.getAddress());
        assertEquals(VALID_CUISINE_TYPE, restaurant.getCuisineType().getDisplayName());

        String expectedStorageHours = "SEG-DOM:" + VALID_OPENING_HOURS;
        assertEquals(expectedStorageHours, restaurant.getOpeningHoursForStorage());
        assertEquals(VALID_OWNER_ID, restaurant.getOwnerUserId());
    }

    @Test
    @DisplayName("Construtor sem ID deve criar objeto válido")
    void construtorSemId_deveCriarObjetoValido() {
        Restaurant restaurant = new Restaurant(VALID_NAME, VALID_ADDRESS, VALID_CUISINE_TYPE,
                VALID_OPENING_HOURS, VALID_OWNER_ID);

        assertNull(restaurant.getId());
        assertEquals(VALID_NAME, restaurant.getName());
    }

    @Test
    @DisplayName("validateName deve lançar exceção quando nome for nulo")
    void validateName_deveLancarExcecaoQuandoNomeForNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Restaurant(null, VALID_ADDRESS, VALID_CUISINE_TYPE,
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );
        assertEquals("Restaurant name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("validateName deve lançar exceção quando nome for vazio")
    void validateName_deveLancarExcecaoQuandoNomeForVazio() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Restaurant(" ", VALID_ADDRESS, VALID_CUISINE_TYPE,
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );
        assertEquals("Restaurant name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("validateName deve lançar exceção quando nome exceder 120 caracteres")
    void validateName_deveLancarExcecaoQuandoNomeExcederLimite() {
        String nomeLongo = "a".repeat(121);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Restaurant(nomeLongo, VALID_ADDRESS, VALID_CUISINE_TYPE,
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );
        assertEquals("Restaurant name cannot exceed 120 characters", exception.getMessage());
    }

    @Test
    @DisplayName("validateAddress deve lançar exceção quando endereço for nulo")
    void validateAddress_deveLancarExcecaoQuandoEnderecoForNulo() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Restaurant(VALID_NAME, null, VALID_CUISINE_TYPE,
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );
        assertEquals("Restaurant address cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("validateAddress deve lançar exceção quando endereço for vazio")
    void validateAddress_deveLancarExcecaoQuandoEnderecoForVazio() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Restaurant(VALID_NAME, " ", VALID_CUISINE_TYPE,
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );
        assertEquals("Restaurant address cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("validateAddress deve lançar exceção quando endereço exceder 255 caracteres")
    void validateAddress_deveLancarExcecaoQuandoEnderecoExceder255Caracteres() {
        String enderecoLongo = "a".repeat(256);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Restaurant(VALID_NAME, enderecoLongo, VALID_CUISINE_TYPE,
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );

        assertEquals("Restaurant address cannot exceed 255 characters", exception.getMessage());
    }

    @Test
    @DisplayName("validateCuisineType deve lançar exceção quando cuisineType for nulo")
    void validateCuisineType_deveLancarExcecaoQuandoCuisineTypeForNulo() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new Restaurant(VALID_NAME, VALID_ADDRESS, null,
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );
        assertEquals("Cuisine type cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("validateCuisineType deve lançar exceção quando cuisineType for vazio")
    void validateCuisineType_deveLancarExcecaoQuandoCuisineTypeForVazio() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new Restaurant(VALID_NAME, VALID_ADDRESS, " ",
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );
        assertEquals("Cuisine type cannot be null or empty", exception.getMessage());
    }

    @Test
    void validateCuisineType_deveLancarExcecaoQuandoCuisineTypeExceder50Caracteres() {
        String cuisineTypeLongo = "a".repeat(51);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Restaurant(VALID_NAME, VALID_ADDRESS, cuisineTypeLongo,
                        VALID_OPENING_HOURS, VALID_OWNER_ID)
        );

        String expectedMessageStart = "Invalid cuisine type 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'";
        assertTrue(exception.getMessage().startsWith(expectedMessageStart));
    }

    @Test
    @DisplayName("setOwnerUserId deve aceitar valor válido")
    void setOwnerUserIdDeveAceitarValorValido() {
        Restaurant restaurant = new Restaurant(VALID_NAME, VALID_ADDRESS, VALID_CUISINE_TYPE,
                VALID_OPENING_HOURS, VALID_OWNER_ID);

        UUID novoOwnerId = UUID.randomUUID();
        restaurant.setOwnerUserId(novoOwnerId);

        assertEquals(novoOwnerId, restaurant.getOwnerUserId());
    }

    @Test
    @DisplayName("setOwnerUserId deve lançar exceção quando valor for nulo")
    void setOwnerUserIdDeveLancarExcecaoQuandoValorForNulo() {
        Restaurant restaurant = new Restaurant(VALID_NAME, VALID_ADDRESS, VALID_CUISINE_TYPE,
                VALID_OPENING_HOURS, VALID_OWNER_ID);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restaurant.setOwnerUserId(null)
        );

        assertEquals("Owner User ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("setName deve aceitar nome válido")
    void setNameDeveAceitarNomeValido() {
        Restaurant restaurant = new Restaurant(VALID_NAME, VALID_ADDRESS, VALID_CUISINE_TYPE,
                VALID_OPENING_HOURS, VALID_OWNER_ID);

        String novoNome = "Novo Restaurante";
        restaurant.setName(novoNome);

        assertEquals(novoNome, restaurant.getName());
    }

    @Test
    @DisplayName("setAddress deve aceitar endereço válido")
    void setAddressDeveAceitarEnderecoValido() {
        Restaurant restaurant = new Restaurant(VALID_NAME, VALID_ADDRESS, VALID_CUISINE_TYPE,
                VALID_OPENING_HOURS, VALID_OWNER_ID);

        String novoEndereco = "Rua Nova, 456";
        restaurant.setAddress(novoEndereco);

        assertEquals(novoEndereco, restaurant.getAddress());
    }

    @Test
    @DisplayName("setCuisineType deve aceitar tipo válido")
    void setCuisineTypeDeveAceitarTipoValido() {
        Restaurant restaurant = new Restaurant(
                VALID_NAME,
                VALID_ADDRESS,
                VALID_CUISINE_TYPE,
                VALID_OPENING_HOURS,
                VALID_OWNER_ID
        );

        String novoTipo = "Japonesa";
        restaurant.setCuisineType(novoTipo);

        assertEquals(CuisineType.JAPONESA, restaurant.getCuisineType());
    }

    @Test
    @DisplayName("setOpeningHours deve aceitar string de horário válida")
    void setOpeningHours_deveAceitarHorarioValido() {
        Restaurant restaurant = new Restaurant(
                VALID_NAME,
                VALID_ADDRESS,
                VALID_CUISINE_TYPE,
                VALID_OPENING_HOURS,
                VALID_OWNER_ID
        );

        String novoHorario = "10:30-20:45";
        restaurant.setOpeningHours(novoHorario);

        assertEquals("SEG-DOM:10:30-20:45", restaurant.getOpeningHoursForStorage());
    }

    @Test
    @DisplayName("getOpeningHoursForDisplay deve retornar formato amigável")
    void getOpeningHoursForDisplay_deveRetornarFormatoCorreto() {
        Restaurant restaurant = new Restaurant(
                VALID_NAME,
                VALID_ADDRESS,
                VALID_CUISINE_TYPE,
                "08:00-22:00",
                VALID_OWNER_ID
        );

        String esperado = "Seg-Dom: 08:00-22:00";
        assertEquals(esperado, restaurant.getOpeningHoursForDisplay());
    }
}