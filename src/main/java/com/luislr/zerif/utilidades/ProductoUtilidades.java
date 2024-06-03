package com.luislr.zerif.utilidades;

import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.ValoracionProducto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ProductoUtilidades {
    public void calcularMediaValoracion(List<Producto> listaProducto){
        // Calculamos la media de valoraciones para cada producto y establecerla
        listaProducto.forEach(producto -> {
            double mediaValoraciones = producto.getValoraciones().stream()
                    .mapToInt(ValoracionProducto::getValoracion)
                    .average()
                    .orElse(0.0); // Si no hay valoraciones, la media es 0.0
            mediaValoraciones = Math.round(mediaValoraciones * 100.0) / 100.0;
            producto.setMediaValoraciones(mediaValoraciones);
        });
    }

}
