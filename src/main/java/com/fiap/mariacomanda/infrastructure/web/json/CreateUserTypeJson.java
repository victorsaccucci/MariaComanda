package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserTypeJson {

    @NotBlank
    private String name; // Nome descritivo (ex: Cliente, Dono do Restaurante)

    @NotBlank
    @Pattern(regexp = "(?i)customer|owner", message = "subType must be CUSTOMER or OWNER")
    private String subType; // Valores permitidos: CUSTOMER, OWNER
}
