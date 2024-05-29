package com.luislr.zerif.utilidades;

import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.ValoracionProducto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.OptionalDouble;

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

    public double[] obtenerRangoDePrecios(List<Producto> listaProductos) {
        // Encontrar el precio más bajo
        OptionalDouble precioMin = listaProductos.stream()
                .mapToDouble(Producto::getPrecio)
                .min();

        // Encontrar el precio más alto
        OptionalDouble precioMax = listaProductos.stream()
                .mapToDouble(Producto::getPrecio)
                .max();

        // Si ambos valores están presentes, retornar el rango, de lo contrario, retornar un rango predeterminado
        if (precioMin.isPresent() && precioMax.isPresent()) {
            return new double[]{precioMin.getAsDouble(), precioMax.getAsDouble()};
        } else {
            return new double[]{0.0, 1.0};
        }
    }
}
