package com.luislr.zerif.dto.tarjeta;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TarjetaCompraDto {
    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Size(min = 16, max = 16, message = "El número de tarjeta debe tener 16 dígitos")
    private String numTarjeta;

    @NotBlank(message = "El CV es obligatorio")
    @Size(min = 3, max = 4, message = "El CV debe tener entre 3 y 4 dígitos")
    private String cv;

    @NotNull(message = "El mes de caducidad es obligatorio")
    @Min(value = 1, message = "El mes debe ser un número entre 1 y 12")
    @Max(value = 12, message = "El mes debe ser un número entre 1 y 12")
    private Integer mesCaducidad;

    @NotNull(message = "El año de caducidad es obligatorio")
    @Pattern(regexp = "\\d{2}", message = "El año debe ser los dos últimos dígitos")
    private String yearCaducidad;
}
