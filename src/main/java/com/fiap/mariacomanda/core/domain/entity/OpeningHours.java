package com.fiap.mariacomanda.core.domain.entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

public record OpeningHours(
    Set<DayOfWeek> daysOfWeek,
    LocalTime openTime,
    LocalTime closeTime
) {
    
    public OpeningHours {
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            throw new IllegalArgumentException("Days of week cannot be null or empty");
        }
        if (openTime == null) {
            throw new IllegalArgumentException("Open time cannot be null");
        }
        if (closeTime == null) {
            throw new IllegalArgumentException("Close time cannot be null");
        }
        if (!openTime.isBefore(closeTime)) {
            throw new IllegalArgumentException("Open time must be before close time");
        }
    }
    
    public static OpeningHours fromString(String openingHoursStr) {
        if (openingHoursStr == null || openingHoursStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Opening hours string cannot be null or empty");
        }
        
        try {
            String trimmed = openingHoursStr.trim().toUpperCase();
            
            // Verifica se tem dias da semana (contém : antes do horário)
            if (trimmed.startsWith("SEG-SEX:")) {
                String timePart = trimmed.substring(8); // Remove "SEG-SEX:"
                String[] timeParts = timePart.split("-");
                
                LocalTime open = LocalTime.parse(timeParts[0], DateTimeFormatter.ofPattern("H:mm"));
                LocalTime close = LocalTime.parse(timeParts[1], DateTimeFormatter.ofPattern("H:mm"));
                
                return new OpeningHours(
                    Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                    open,
                    close
                );
            }
            
            if (trimmed.startsWith("SAB-DOM:")) {
                String timePart = trimmed.substring(8); // Remove "SAB-DOM:"
                String[] timeParts = timePart.split("-");
                
                LocalTime open = LocalTime.parse(timeParts[0], DateTimeFormatter.ofPattern("H:mm"));
                LocalTime close = LocalTime.parse(timeParts[1], DateTimeFormatter.ofPattern("H:mm"));
                
                return new OpeningHours(
                    Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                    open,
                    close
                );
            }
            
            if (trimmed.startsWith("SEG-DOM:")) {
                String timePart = trimmed.substring(8); // Remove "SEG-DOM:"
                String[] timeParts = timePart.split("-");
                
                LocalTime open = LocalTime.parse(timeParts[0], DateTimeFormatter.ofPattern("H:mm"));
                LocalTime close = LocalTime.parse(timeParts[1], DateTimeFormatter.ofPattern("H:mm"));
                
                return new OpeningHours(
                    Set.of(DayOfWeek.values()),
                    open,
                    close
                );
            }
            
            // Formato simples: "10:00-22:00" (assume todos os dias)
            if (trimmed.contains("-")) {
                String[] parts = trimmed.split("-");
                if (parts.length == 2) {
                    LocalTime open = LocalTime.parse(parts[0], DateTimeFormatter.ofPattern("H:mm"));
                    LocalTime close = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:mm"));
                    
                    return new OpeningHours(
                        Set.of(DayOfWeek.values()),
                        open,
                        close
                    );
                }
            }
            
            throw new IllegalArgumentException("Restaurant opening hours must be in format HH:MM-HH:MM or SEG-SEX:HH:MM-HH:MM (e.g., '10:00-22:00' or 'SEG-SEX:10:00-22:00')");
            
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Restaurant opening hours must be in format HH:MM-HH:MM or SEG-SEX:HH:MM-HH:MM (e.g., '10:00-22:00' or 'SEG-SEX:10:00-22:00')");
        }
    }
    
    private static Set<DayOfWeek> parseDays(String daysPart) {
        return switch (daysPart) {
            case "SEG-SEX" -> Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
            case "SAB-DOM" -> Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
            case "SEG-DOM" -> Set.of(DayOfWeek.values());
            default -> throw new IllegalArgumentException("Invalid days format. Use SEG-SEX, SAB-DOM, or SEG-DOM");
        };
    }
    
    public String toDisplayString() {
        String timeRange = openTime.format(DateTimeFormatter.ofPattern("HH:mm")) + 
                          "-" + 
                          closeTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        
        if (daysOfWeek.size() == 7) {
            return "Seg-Dom: " + timeRange;
        } else if (daysOfWeek.equals(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))) {
            return "Seg-Sex: " + timeRange;
        } else if (daysOfWeek.equals(Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))) {
            return "Sáb-Dom: " + timeRange;
        }
        
        return timeRange;
    }
    
    public String toStorageString() {
        String timeRange = openTime.format(DateTimeFormatter.ofPattern("HH:mm")) + 
                          "-" + 
                          closeTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        
        if (daysOfWeek.size() == 7) {
            return "SEG-DOM:" + timeRange;
        } else if (daysOfWeek.equals(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))) {
            return "SEG-SEX:" + timeRange;
        } else if (daysOfWeek.equals(Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))) {
            return "SAB-DOM:" + timeRange;
        }
        
        return timeRange;
    }
}