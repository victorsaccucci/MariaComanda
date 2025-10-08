package com.fiap.mariacomanda.core.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {
    @Test
    @DisplayName("Deve criar MenuItem pelo construtor completo e preencher todos os campos")
    void construtorCompleto_deveCriarMenuItemCorretamente() {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        MenuItem item = new MenuItem(id, restaurantId, "Ceviche", "Prato Peruano", BigDecimal.TEN, true, "/img/ceviche.jpg");

        assertEquals(id, item.getId());
        assertEquals(restaurantId, item.getRestaurantId());
        assertEquals("Ceviche", item.getName());
        assertEquals("Prato Peruano", item.getDescription());
        assertEquals(BigDecimal.TEN, item.getPrice());
        assertTrue(item.isDineInOnly());
        assertEquals("/img/ceviche.jpg", item.getPhotoPath());
    }

    @Test
    @DisplayName("Setter de preço deve lançar IllegalArgumentException se valor for inválido")
    void setterPrice_deveValidarPreco() {
        UUID restaurantId = UUID.randomUUID();
        MenuItem item = new MenuItem(restaurantId, "Teste", "desc", BigDecimal.ONE, false, null);
        assertThrows(IllegalArgumentException.class, () -> item.setPrice(null));
        assertThrows(IllegalArgumentException.class, () -> item.setPrice(BigDecimal.ZERO));
    }

    private final UUID restaurantId = UUID.randomUUID();
    private final BigDecimal price = BigDecimal.valueOf(25.50);

    @Test
    @DisplayName("Deve aceitar e retornar um UUID válido para o restaurante ao alterar via setter")
    void validateRestaurantId_deveRetornarUUIDQuandoValido() {
        MenuItem item = new MenuItem(restaurantId, "Pizza", "desc", price, false, null);

        UUID novoRestaurante = UUID.randomUUID();
        item.setRestaurantId(novoRestaurante);

        assertEquals(novoRestaurante, item.getRestaurantId());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao tentar definir restaurantId como null via setter")
    void validateRestaurantId_deveLancarExcecaoQuandoUUIDForNulo() {
        MenuItem item = new MenuItem(restaurantId, "Pizza", "desc", price, false, null);

        assertThrows(IllegalArgumentException.class, () -> {
            item.setRestaurantId(null);
        });
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o nome for nulo")
    void validateName_deveLancarExcecaoQuandoNomeForNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new MenuItem(restaurantId, null, "desc", price, false, null)
        );
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o nome for vazio ou só espaços")
    void validateName_deveLancarExcecaoQuandoNomeForVazioOuEspacos() {
        assertThrows(IllegalArgumentException.class, () ->
            new MenuItem(restaurantId, "   ", "desc", price, false, null)
        );
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando o nome exceder 120 caracteres")
    void validateName_deveLancarExcecaoQuandoNomeMuitoLongo() {
        String nomeMuitoLongo = "A".repeat(121);
        assertThrows(IllegalArgumentException.class, () ->
            new MenuItem(restaurantId, nomeMuitoLongo, "desc", price, false, null)
        );
    }

    @Test
    @DisplayName("Deve aceitar um preço válido maior que zero")
    void validatePrice_deveAceitarPrecoValido() {
        BigDecimal precoValido = new BigDecimal("10.00");
        MenuItem item = new MenuItem(restaurantId, "Sushi", "desc", precoValido, false, null);
        assertEquals(precoValido, item.getPrice());
    }

    @Test
    @DisplayName("Deve permitir alterar o nome para um valor válido usando setName")
    void setName_deveAceitarNomeValido() {
        MenuItem item = new MenuItem(restaurantId, "Original", "desc", price, false, null);
        item.setName("Novo Nome");
        assertEquals("Novo Nome", item.getName());
    }

    @Test
    @DisplayName("Deve permitir alterar o preço para um valor válido usando setPrice")
    void setPrice_deveAceitarPrecoValido() {
        MenuItem item = new MenuItem(restaurantId, "Teste", "desc", price, false, null);
        item.setPrice(new BigDecimal("30.00"));
        assertEquals(new BigDecimal("30.00"), item.getPrice());
    }
}