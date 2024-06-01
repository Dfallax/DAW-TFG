package com.luislr.zerif.utilidades;

import com.luislr.zerif.dto.direccion.DireccionCompraDto;
import com.luislr.zerif.entidades.Direccion;

public class DireccionUtilidades {

    public static Direccion convertToEntity(DireccionCompraDto direccionDto) {
        if (direccionDto == null) return null;

        return Direccion.builder()
                .calle(direccionDto.getCalle())
                .numero(direccionDto.getNumero())
                .piso(direccionDto.getPiso())
                .puerta(direccionDto.getPuerta().charAt(0))
                .ciudad(direccionDto.getCiudad())
                .build();
    }

    public static DireccionCompraDto convertToDto(Direccion direccion) {
        if (direccion == null) return null;

        return new DireccionCompraDto(
                direccion.getCalle(),
                direccion.getNumero(),
                direccion.getPiso(),
                String.valueOf(direccion.getPuerta()),
                direccion.getCiudad()
        );
    }
}
