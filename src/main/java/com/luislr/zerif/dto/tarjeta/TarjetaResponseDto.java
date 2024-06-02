package com.luislr.zerif.dto.tarjeta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarjetaResponseDto {
    private Long id;
    private String numTarjeta;
    private String cv;
    private String fechCaducidad;
}
