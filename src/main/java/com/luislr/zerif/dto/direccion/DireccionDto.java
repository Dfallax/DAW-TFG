package com.luislr.zerif.dto.direccion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DireccionDto {
    private Long id;
    private String calle;
    private int numero;
    private int piso;
    private char puerta;
    private String ciudad;
}
