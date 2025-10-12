package com.fiap.mariacomanda.infrastructure.web.json;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Schema(description = "Dados para atualização de um restaurante (todos os campos são opcionais)")
public class UpdateRestaurantJson {

    @Schema(description = "Nome do restaurante", example = "Novo Nome do Restaurante")
    private String name;

    @Schema(description = "Endereço completo do restaurante", example = "Nova Rua, 456 - Bairro")
    private String address;

    @Schema(description = "Tipo de culinária do restaurante", 
            example = "Japonesa",
            allowableValues = {"Brasileira", "Italiana", "Japonesa", "Chinesa", "Mexicana", "Francesa", "Indiana", "Tailandesa", "Árabe", "Americana", "Vegetariana", "Vegana", "Fast Food", "Pizzaria", "Churrascaria", "Frutos do Mar"})
    private String cuisineType;

    @Schema(description = "Horário de funcionamento. Formatos: HH:MM-HH:MM, SEG-SEX:HH:MM-HH:MM, SAB-DOM:HH:MM-HH:MM, SEG-DOM:HH:MM-HH:MM", 
            example = "SAB-DOM:11:00-23:00")
    private String openingHours;

    @Schema(description = "UUID do novo proprietário (deve ser do tipo OWNER)", 
            example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID ownerUserId;
}
