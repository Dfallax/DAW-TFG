package com.luislr.zerif.dto.direccion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionCreateDto {
    @NotBlank(message = "La calle es obligatoria.")
    private String calle="Este";

    @NotNull(message = "El número es obligatorio.")
    @Min(value = 1, message = "El número debe ser mayor que 0.")
    private Integer numero=4;

    @NotNull(message = "El piso es obligatorio.")
    @Min(value = 0, message = "El piso no puede ser negativo.")
    private Integer piso=3;

    @NotBlank(message = "La puerta es obligatoria.")
    @Pattern(regexp = "^[A-Za-z]$", message = "La puerta debe ser un solo carácter alfabético.")
    private String puerta="P";

    @NotBlank(message = "La ciudad es obligatoria.")
    private String ciudad="Pi pi";
}
