package com.luislr.zerif.dto.direccion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionResponseDto {
    private Long id;
    private String calle;
    private Integer numero;
    private Integer piso;
    private String puerta;
    private String ciudad;
}
