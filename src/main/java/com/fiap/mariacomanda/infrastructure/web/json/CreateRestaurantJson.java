package com.fiap.mariacomanda.infrastructure.web.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Schema(description = "Dados para criação de um restaurante")
public class CreateRestaurantJson {

    @Schema(description = "Nome do restaurante", example = "Restaurante Exemplo", required = true)
    private String name;
    
    @Schema(description = "Endereço completo do restaurante", example = "Rua X, 123 - Centro", required = true)
    private String address;
    
    @Schema(description = "Tipo de culinária do restaurante", 
            example = "Italiana", 
            required = true,
            allowableValues = {"Brasileira", "Italiana", "Japonesa", "Chinesa", "Mexicana", "Francesa", "Indiana", "Tailandesa", "Árabe", "Americana", "Vegetariana", "Vegana", "Fast Food", "Pizzaria", "Churrascaria", "Frutos do Mar"})
    private String cuisineType;
    
    @Schema(description = "Horário de funcionamento do restaurante. Formatos aceitos: HH:MM-HH:MM (todos os dias), SEG-SEX:HH:MM-HH:MM (segunda a sexta), SAB-DOM:HH:MM-HH:MM (sábado e domingo), SEG-DOM:HH:MM-HH:MM (todos os dias)", 
            example = "SEG-SEX:09:00-22:00", 
            required = true)
    private String openingHours;
    
    @Schema(description = "UUID do usuário proprietário do restaurante (deve ser do tipo OWNER)", 
            example = "f47ac10b-58cc-4372-a567-0e02b2c3d479", 
            required = true)
    private UUID ownerUserId;

}