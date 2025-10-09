package com.fiap.mariacomanda.infrastructure.web.json;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserTypeJson {

    @Size(max = 100)
    private String name;

    @Pattern(regexp = "(?i)customer|owner", message = "subType must be CUSTOMER or OWNER")
    private String subType;
}
