package com.luislr.zerif.dto.tarjeta;

import lombok.Data;

@Data
public class TarjetaDto {
    private Long id;
    private String numTarjeta;
    private String cv;
    private Integer mesCaducidad;
    private Integer anioCaducidad;
    private Long pedidoId;
}
