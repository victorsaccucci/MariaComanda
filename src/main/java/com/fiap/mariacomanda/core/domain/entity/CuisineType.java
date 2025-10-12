package com.fiap.mariacomanda.core.domain.entity;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum CuisineType {
    BRASILEIRA("Brasileira"),
    ITALIANA("Italiana"),
    JAPONESA("Japonesa"),
    CHINESA("Chinesa"),
    MEXICANA("Mexicana"),
    FRANCESA("Francesa"),
    INDIANA("Indiana"),
    TAILANDESA("Tailandesa"),
    ARABE("Árabe"),
    AMERICANA("Americana"),
    VEGETARIANA("Vegetariana"),
    VEGANA("Vegana"),
    FAST_FOOD("Fast Food"),
    PIZZARIA("Pizzaria"),
    CHURRASCARIA("Churrascaria"),
    FRUTOS_DO_MAR("Frutos do Mar");
    
    private final String displayName;
    
    CuisineType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static CuisineType fromString(String cuisineTypeStr) {
        if (cuisineTypeStr == null || cuisineTypeStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Cuisine type cannot be null or empty");
        }
        
        String normalized = cuisineTypeStr.trim().toUpperCase();
        
        for (CuisineType type : values()) {
            if (type.name().equals(normalized) || 
                type.displayName.toUpperCase().equals(normalized)) {
                return type;
            }
        }
        
        String availableOptions = Arrays.stream(values())
            .map(CuisineType::getDisplayName)
            .collect(Collectors.joining(", "));
            
        throw new IllegalArgumentException(
            String.format("Invalid cuisine type '%s'. Available options: %s", 
                cuisineTypeStr, availableOptions)
        );
    }
}