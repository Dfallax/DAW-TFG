package com.luislr.zerif.utilidades;

import com.luislr.zerif.entidades.ArticuloPedido;
import com.luislr.zerif.entidades.Pedido;
import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.Usuario;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class PedidoUtilidades {
    public Pedido crearPedidoConProducto(Usuario usuario, Producto producto, int cantidad){
        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .estado(Pedido.EstadoPedido.PENDIENTE)
                .build();
        List<ArticuloPedido> articuloPedidos = Collections.singletonList(
                ArticuloPedido.builder()
                        .producto(producto)
                        .cantidad(cantidad)
                        .pedido(pedido)
                        .precioUnidad(BigDecimal.valueOf(producto.getPrecio()))
                        .build()
        );
        pedido.setArticulos(articuloPedidos);
        return pedido;
    }
}
