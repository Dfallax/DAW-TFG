package com.luislr.zerif.dto.receptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceptorResponseDto {

    private Long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private Long idPedido;
}

