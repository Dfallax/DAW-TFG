package com.luislr.zerif.controladores;

import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.Subcategoria;
import com.luislr.zerif.servicios.ProductoService;
import com.luislr.zerif.servicios.SubcategoriaService;
import com.luislr.zerif.servicios.CategoriaService;
import com.luislr.zerif.utilidades.ProductoUtilidades;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;

    @GetMapping( "/lista/filtrada")
    public String listadoFiltrado(@RequestParam("nombre") String nombre, Model model) {
        model.addAttribute("listaProductos", productoService.findByNombreContainsIgnoreCase(nombre));
        return "fragmentos/table-buscadorNav::table-buscadorNav";
    }

    @GetMapping("/detalle/{nombre}")
    public String detalle(@PathVariable String nombre, Model model) {
        // Primero obtenemos el producto
        Optional<Producto> producto = Optional.ofNullable(productoService.findByNombre(nombre));
        if (producto.isPresent()) {
            // Hacemos una busqueda de los productos del mimmo tipo
            List<Producto> productosTipo = productoService.findByCategoria(producto.get().getCategoria());
            // Aleatorizar el orden de la lista productosTipo
            Collections.shuffle(productosTipo);
            if (!producto.get().getCategoria().getNombre().equals("eventos")){
                List<Producto> listaProducto = productosTipo.subList(0, 6);
                model.addAttribute("listaProducto",listaProducto);
            }
        }else {
            return "redirect:/pedido/?error=true";
        }
        model.addAttribute("producto", producto);
        return "productos/detalle-producto";
    }

    @GetMapping("/tipo/{tipo}")
    public String productosTipo(@PathVariable String tipo, Model model) {
        Optional<Categoria> tipoProducto = categoriaService.findByNombre(tipo.toLowerCase());
        if (tipoProducto.isPresent()) {
            List<Producto> listaProductoTipo = productoService.findByCategoria(tipoProducto.get());

            ProductoUtilidades.calcularMediaValoracion(listaProductoTipo);

            model.addAttribute("tipo", tipo.substring(0, 1).toUpperCase() + tipo.substring(1));
            model.addAttribute("listaSubtipos", tipoProducto.get().getSubcategorias());
            model.addAttribute("listaProducto", listaProductoTipo);



            return "productos/tipo-producto";
        } else {
            return "excepciones/404";
        }
    }

    @GetMapping("/tipo/{tipo}/{subtipo}")
    public String productosSubtipo(@PathVariable String tipo, @PathVariable String subtipo, Model model) {

        Optional<Categoria> tipoProducto = categoriaService.findByNombre(tipo.toLowerCase());
        Optional<Subcategoria> subtipoProducto = subcategoriaService.findByNombre(subtipo);

        if (tipoProducto.isPresent() & subtipoProducto.isPresent()) {
            List<Producto> listaProductoTipoYSubtipo = productoService.findByCategoriaAndSubcategoria(tipoProducto.get(), subtipoProducto.get());

            ProductoUtilidades.calcularMediaValoracion(listaProductoTipoYSubtipo);

            model.addAttribute("listaProducto", listaProductoTipoYSubtipo);
            return "fragmentos/span-producto::span-producto";
        } else {
            return "excepciones/404";
        }
    }

    @GetMapping("/ordenar/show")
    public String showModalOrdenar(){
        return "modales/opcionesOrdenar::opcionesOrdenar";
    }

    @GetMapping("/ordenar")
    public String ordenarProductosPor(@RequestParam("tipo") String tipo,
                                      @RequestParam("subtipo") String subtipo,
                                      @RequestParam("orden") int orden
            ,Model model){
        List<Producto> listaProducto;
        Optional<Categoria> tipoProducto = categoriaService.findByNombre(tipo.toLowerCase());
        Optional<Subcategoria> subtipoProducto = subcategoriaService.findByNombre(subtipo);

        if (tipoProducto.isPresent()){// Verificamos que el tipo esta
            if (subtipo.equals("all")) {//Verificamos si se ha selecionado un subtipo
                listaProducto = productoService.findByCategoria(tipoProducto.get());
            }else {
                if (subtipoProducto.isPresent()){
                    listaProducto = productoService.findByCategoriaAndSubcategoria(tipoProducto.get(), subtipoProducto.get());
                }else {
                    return "excepciones/404";
                }
            }

            ProductoUtilidades.calcularYOrdenarProductos(listaProducto, orden);

            model.addAttribute("listaProducto", listaProducto);
            return "fragmentos/span-producto::span-producto";
        }else{
            return "excepciones/404";
        }
    }

    @GetMapping("/filtrar/show")
    public String showModalFiltrar(@RequestParam("tipo") String tipo,
                                   @RequestParam("subtipo") String subtipo,
                                   @RequestParam("orden") int orden,
                                   Model model){


        List<Producto> listaProducto;
        Optional<Categoria> tipoProducto = categoriaService.findByNombre(tipo.toLowerCase());
        Optional<Subcategoria> subtipoProducto = subcategoriaService.findByNombre(subtipo);

        if (tipoProducto.isPresent()){// Verificamos que el tipo esta
            if (subtipo.equals("all")) {//Verificamos si se ha selecionado un subtipo
                listaProducto = productoService.findByCategoria(tipoProducto.get());
            }else {
                if (subtipoProducto.isPresent()){
                    listaProducto = productoService.findByCategoriaAndSubcategoria(tipoProducto.get(), subtipoProducto.get());
                }else {
                    return "excepciones/404";
                }
            }

            ProductoUtilidades.calcularYOrdenarProductos(listaProducto, orden);

            // Obtener precios mínimo y máximo
            double precioMin = listaProducto.stream().mapToDouble(Producto::getPrecio).min().orElse(0.0);
            double precioMax = listaProducto.stream().mapToDouble(Producto::getPrecio).max().orElse(1000.0);

            model.addAttribute("precioMin", precioMin);
            model.addAttribute("precioMax", precioMax);
            return "modales/opcionesFiltrar::opcionesFiltrar";
        }else{
            return "excepciones/404";
        }
    }

    @GetMapping("/filtrar")
    public String filtrar(@RequestParam("tipo") String tipo,
                          @RequestParam("subtipo") String subtipo,
                          @RequestParam("orden") int orden,
                          @RequestParam("precioMin") double precioMin,
                          @RequestParam("precioMax") double precioMax,
                          Model model) {

        List<Producto> listaProducto;
        Optional<Categoria> tipoProducto = categoriaService.findByNombre(tipo.toLowerCase());
        Optional<Subcategoria> subtipoProducto = subcategoriaService.findByNombre(subtipo);

        if (tipoProducto.isPresent()) { // Verificamos que el tipo está
            if (subtipo.equals("all")) { // Verificamos si se ha seleccionado un subtipo
                listaProducto = productoService.findByCategoria(tipoProducto.get());
            } else {
                if (subtipoProducto.isPresent()) {
                    listaProducto = productoService.findByCategoriaAndSubcategoria(tipoProducto.get(), subtipoProducto.get());
                } else {
                    return "excepciones/404";
                }
            }

            // Filtrar por precio
            listaProducto = listaProducto.stream()
                    .filter(p -> p.getPrecio() >= precioMin && p.getPrecio() <= precioMax)
                    .collect(Collectors.toList());

           ProductoUtilidades.calcularYOrdenarProductos(listaProducto, orden);

            model.addAttribute("listaProducto", listaProducto);
            return "fragmentos/span-producto::span-producto";
        } else {
            return "excepciones/404";
        }
    }

}

