package com.luislr.zerif.dto.tarjeta;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarjetaCreateDto {
    @NotBlank(message = "{tarjeta.error.numero.obligatorio}")
    @Size(min = 16, max = 16, message = "{tarjeta.error.numero.size}")
    private String numTarjeta;

    @NotBlank(message = "{tarjeta.error.cv.obligatorio}")
    @Size(min = 3, max = 4, message = "{tarjeta.error.cv.size}")
    private String cv;

    @NotNull(message = "{tarjeta.error.mesCaducidad.obligatorio}")
    @Min(value = 1, message = "{tarjeta.error.mesCaducidad.min}")
    @Max(value = 12, message = "{tarjeta.error.mesCaducidad.max}")
    private Integer mesCaducidad;

    @NotNull(message = "{tarjeta.error.yearCaducidad.obligatorio}")
    @Pattern(regexp = "\\d{2}", message = "{tarjeta.error.yearCaducidad.pattern}")
    private String yearCaducidad;
}
