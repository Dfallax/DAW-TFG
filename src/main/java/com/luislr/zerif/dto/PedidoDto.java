package com.luislr.zerif.dto;

import com.luislr.zerif.dto.direccion.DireccionCreateDto;
import com.luislr.zerif.dto.tarjeta.TarjetaCreateDto;
import com.luislr.zerif.entidades.Pedido.EstadoPedido;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoDto {

    private Long id;

    @PastOrPresent(message = "La fecha del pedido no puede ser futura")
    private LocalDate fechaPedido;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "La dirección es obligatoria")
    private DireccionCreateDto direccion;

    @NotNull(message = "La tarjeta es obligatoria")
    private TarjetaCreateDto tarjeta;

    @NotNull(message = "La lista de artículos no puede estar vacía")
    private List<ArticuloPedidoDto> articulos;

    @NotNull(message = "El estado del pedido es obligatorio")
    private EstadoPedido estado;

    @DecimalMin(value = "0.0", inclusive = false, message = "El total del pedido debe ser mayor que cero")
    private BigDecimal total;

    public BigDecimal getTotal() {
        if (articulos == null || articulos.isEmpty()) return BigDecimal.ZERO;

        BigDecimal total = articulos.stream()
                .map(ArticuloPedidoDto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
