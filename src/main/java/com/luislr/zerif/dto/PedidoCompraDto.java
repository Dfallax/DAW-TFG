package com.luislr.zerif.dto;

import com.luislr.zerif.dto.direccion.DireccionCreateDto;
import com.luislr.zerif.dto.tarjeta.TarjetaCreateDto;
import com.luislr.zerif.entidades.Pedido;
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

    private Pedido pedido;

    @Valid
    private TarjetaCreateDto tarjeta;

    @Valid
    private DireccionCreateDto direccion;
}
