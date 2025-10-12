package com.fiap.mariacomanda.core.domain.entity;

public enum DayOfWeek {
    MONDAY("Segunda"),
    TUESDAY("Terça"),
    WEDNESDAY("Quarta"),
    THURSDAY("Quinta"),
    FRIDAY("Sexta"),
    SATURDAY("Sábado"),
    SUNDAY("Domingo");
    
    private final String displayName;
    
    DayOfWeek(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}