package com.fiap.mariacomanda.core.domain.entity;

import com.fiap.mariacomanda.core.domain.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuisineTypeTest {

    @Test
    @DisplayName("fromString deve retornar enum correto ao receber displayName (case-insensitive)")
    void fromString_deveRetornarEnumPorDisplayName() {
        assertEquals(CuisineType.BRASILEIRA, CuisineType.fromString("Brasileira"));
        assertEquals(CuisineType.VEGANA, CuisineType.fromString("vegana"));
        assertEquals(CuisineType.FAST_FOOD, CuisineType.fromString("fast food"));
        assertEquals(CuisineType.FRUTOS_DO_MAR, CuisineType.fromString("Frutos do Mar"));
        assertEquals(CuisineType.CHURRASCARIA, CuisineType.fromString("churrascaria"));
        assertEquals(CuisineType.ARABE, CuisineType.fromString("árabe")); // com acento
        assertEquals(CuisineType.PIZZARIA, CuisineType.fromString("PIZZARIA"));
    }
}