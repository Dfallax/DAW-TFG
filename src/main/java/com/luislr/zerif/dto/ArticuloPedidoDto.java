package com.luislr.zerif.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloPedidoDto {
    private Long idPedido;

    private Long idProducto;

    @NotNull(message = "El precio por unidad es obligatorio")
    @Min(value = 0, message = "El precio por unidad no puede ser negativo")
    private BigDecimal precioUnidad;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    public BigDecimal getSubtotal(){
        if(precioUnidad == null) return BigDecimal.ZERO;
        BigDecimal total= precioUnidad.multiply(BigDecimal.valueOf(cantidad));
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
