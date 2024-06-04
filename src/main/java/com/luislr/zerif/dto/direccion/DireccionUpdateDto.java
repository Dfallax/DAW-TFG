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
public class DireccionUpdateDto {
    @NotNull(message = "{direccion.error.id.obligatorio}")
    private Long id;

    @NotBlank(message = "{direccion.error.calle.obligatoria}")
    private String calle;

    @NotNull(message = "{direccion.error.numero.obligatorio}")
    @Min(value = 1, message = "{direccion.error.numero.min}")
    private Integer numero;

    @NotNull(message = "{direccion.error.piso.obligatorio}")
    @Min(value = 0, message = "{direccion.error.piso.min}")
    private Integer piso;

    @NotBlank(message = "{direccion.error.puerta.obligatoria}")
    @Pattern(regexp = "^[A-Za-z]$", message = "{direccion.error.puerta.pattern}")
    private String puerta;

    @NotBlank(message = "{direccion.error.ciudad.obligatoria}")
    private String ciudad;
}
