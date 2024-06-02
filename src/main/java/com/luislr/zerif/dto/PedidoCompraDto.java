package com.luislr.zerif.dto;

import com.luislr.zerif.dto.direccion.DireccionCreateDto;
import com.luislr.zerif.dto.tarjeta.TarjetaCreateDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoCompraDto {

    @Valid
    private TarjetaCreateDto tarjeta;

    @Valid
    private DireccionCreateDto direccion;
}
