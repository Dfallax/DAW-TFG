package com.luislr.zerif.utilidades;

import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.ValoracionProducto;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
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

    public void calcularYOrdenarProductos(List<Producto> listaProducto, int orden){

        calcularMediaValoracion(listaProducto);

        // Ordenar la lista de productos según el orden seleccionado
        switch(orden) {
            case 1: // Orden alfabético
                listaProducto.sort(Comparator.comparing(Producto::getNombre));
                break;
            case 2: // Orden alfabético inverso
                listaProducto.sort(Comparator.comparing(Producto::getNombre).reversed());
                break;
            case 3: // Orden por mayor precio
                listaProducto.sort(Comparator.comparingDouble(Producto::getPrecio).reversed());
                break;
            case 4: // Orden por menor precio
                listaProducto.sort(Comparator.comparingDouble(Producto::getPrecio));
                break;
            case 5: // Ordenar por mayor valoración
                listaProducto.sort(Comparator.comparingDouble(Producto::getMediaValoraciones).reversed());
                break;
            case 6: // Orden por menor valoración
                listaProducto.sort(Comparator.comparingDouble(Producto::getMediaValoraciones));
                break;
            default:
                break;
        }
    }

}
