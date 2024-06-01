package com.luislr.zerif.controladores;

import com.luislr.zerif.dto.direccion.DireccionCompraDto;
import com.luislr.zerif.dto.tarjeta.TarjetaCompraDto;
import com.luislr.zerif.entidades.*;
import com.luislr.zerif.servicios.ArticuloPedidoService;
import com.luislr.zerif.servicios.PedidoService;
import com.luislr.zerif.servicios.ProductoService;
import com.luislr.zerif.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/pedido")
public class PedidoController {


    private final PedidoService pedidoService;
    private final ArticuloPedidoService articuloPedidoService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pedido pedido = pedidoService.obtenerCarrito(username);
        model.addAttribute("carrito", pedido);
        return "pedido/carrito";
    }


    @PostMapping("/carrito/eliminar")
    public ResponseEntity<Void> eliminarArticulo(@RequestParam("idPedido") Long idPedido, @RequestParam("idProducto") Long idProducto) {
        ArticuloPedidoPK id = new ArticuloPedidoPK(idPedido, idProducto);
        pedidoService.eliminarArticulo(id);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/articulo/delete/show/{nombre}")
    public String showModalBorrarArticulo(@AuthenticationPrincipal UserDetails userDetails,
                                          @PathVariable("nombre") String nombre, Model model) {
        Usuario usuario = usuarioService.findByUsername(userDetails.getUsername());
        Producto producto = productoService.findByNombre(nombre);

        Pedido pedido = pedidoService.findCarritoByUsuario(usuario);


        Optional<ArticuloPedido> articulo = articuloPedidoService.findById(ArticuloPedidoPK.builder().idPedido(pedido.getId()).idProducto(producto.getId()).build());
        String deleteMessage;

        if (articulo.isPresent())
            deleteMessage= "¿Confirma el borrado del producto "+producto.getNombre()+" del carrito?";
        else
            return "redirect:/pedido/?error=true";
        model.addAttribute("delete_url", "/pedido/articulo/delete/" + articulo.get().getId().getIdPedido()+"/"+articulo.get().getId().getIdProducto());
        model.addAttribute("delete_title", "Borrado de articulo del carrito");
        model.addAttribute("delete_message", deleteMessage);
        return "modales/modalBorrado::modalBorrado";
    }

    @GetMapping("/articulo/delete/{idPedido}/{idProducto}")
    public String borrarMascota(@PathVariable("idPedido") Long idPedido, @PathVariable("idProducto") Long idProducto) {
        Optional<ArticuloPedido> articuloPedido = articuloPedidoService.findById(ArticuloPedidoPK.builder().idPedido(idPedido).idProducto(idProducto).build());
        articuloPedido.ifPresent(articuloPedidoService::deleteArticuloPedido);
        return "redirect:/pedido/carrito";
    }

    @GetMapping("/actualizar")
    public String actualizarPedido(@RequestParam("nombreProducto") String nombreProducto, @RequestParam("cantidad") int cantidad,
                                   Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pedido pedido = pedidoService.obtenerCarrito(username);
        Producto producto = productoService.findByNombre(nombreProducto);

        Optional<ArticuloPedido> articuloPedido = articuloPedidoService.findById(
                ArticuloPedidoPK.builder()
                        .idPedido(pedido.getId())
                        .idProducto(producto.getId())
                        .build()
        );
        if (articuloPedido.isPresent()){
            articuloPedido.get().setCantidad(cantidad);
            articuloPedidoService.save(articuloPedido.get());
        }else{
            return "/pedido/?error=true";
        }

        model.addAttribute("carrito",pedidoService.obtenerCarrito(username));
        return "fragmentos/span-carrito::span-carrito";
    }

    @GetMapping("/carrito/compra")
    public String procesoCompra(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pedido pedido = pedidoService.obtenerCarrito(username);
        model.addAttribute("carrito", pedido);
        model.addAttribute("tarjeta", new TarjetaCompraDto());
        model.addAttribute("direccion", new DireccionCompraDto());
        return "pedido/proceso-compra";
    }
}
