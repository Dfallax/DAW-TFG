package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "articulo_pedido")
public class ArticuloPedido implements Serializable {

    @EmbeddedId
    private ArticuloPedidoPK id;

    @Column(name = "precio_unidad")
    private BigDecimal precioUnidad;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_pedido", insertable = false, updatable = false)
    @MapsId("idPedido")
    @ToString.Exclude
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    @MapsId("idProducto")
    @ToString.Exclude
    private Producto producto;

    public BigDecimal getSubtotal(){
        if(precioUnidad == null) return BigDecimal.ZERO;
        return precioUnidad.multiply(BigDecimal.valueOf(cantidad));
    }
}
