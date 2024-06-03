package com.luislr.zerif.controladores;

import com.luislr.zerif.dto.PedidoCompraDto;
import com.luislr.zerif.dto.direccion.DireccionCreateDto;
import com.luislr.zerif.dto.direccion.DireccionMapper;
import com.luislr.zerif.dto.tarjeta.TarjetaCreateDto;
import com.luislr.zerif.dto.tarjeta.TarjetaMapper;
import com.luislr.zerif.entidades.*;
import com.luislr.zerif.servicios.*;
import com.luislr.zerif.utilidades.PedidoUtilidades;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    private final DireccionService direccionService;
    private final TarjetaService tarjetaService;
    private final DireccionMapper direccionMapper;
    private final TarjetaMapper tarjetaMapper;

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
    public String procesoCompra(@AuthenticationPrincipal UserDetails userDetails,
                                Model model){
        String username = userDetails.getUsername();
        Pedido pedido = pedidoService.obtenerCarrito(username);
        PedidoCompraDto pedidoCompraDto = new PedidoCompraDto();
        pedidoCompraDto.setTarjeta(new TarjetaCreateDto());
        pedidoCompraDto.setDireccion(new DireccionCreateDto());
        model.addAttribute("pedidoCompraDto",pedidoCompraDto);
        model.addAttribute("pedido",pedido);
        return "pedido/proceso-compra";
    }

    @GetMapping("/producto/compra")
    public String procesoCompraProducto(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String nameProduct,
            @RequestParam int cantidad,
            Model model
    ){
        Usuario usuario = usuarioService.findByUsername(userDetails.getUsername());
        Producto producto = productoService.findByNombre(nameProduct);
        Pedido pedido = PedidoUtilidades.crearPedidoConProducto(usuario, producto, cantidad);

        PedidoCompraDto pedidoCompraDto = new PedidoCompraDto();
        pedidoCompraDto.setTarjeta(new TarjetaCreateDto());
        pedidoCompraDto.setDireccion(new DireccionCreateDto());
        pedidoCompraDto.setIdProducto(producto.getId());
        pedidoCompraDto.setCantidad(cantidad);

        model.addAttribute("pedidoCompraDto",pedidoCompraDto);
        model.addAttribute("pedido",pedido);
        return "pedido/proceso-compra";
    }

    @PostMapping("/compra/submit")
    public String nuevoPedidoSubmit(@AuthenticationPrincipal UserDetails userDetails,
                                    @Valid @ModelAttribute("pedidoCompraDto") PedidoCompraDto pedidoCompraDto,
                                    BindingResult bindingResult,
                                    Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pedido pedido = pedidoService.obtenerCarrito(username);

        if (bindingResult.hasErrors()) {
            log.info("Hay un error");
            bindingResult.getFieldErrors()
                    .forEach(e -> log.info("field: " + e.getField() + ", rejected value: " + e.getRejectedValue()));

            model.addAttribute("pedidoCompraDto", pedidoCompraDto);
            return "pedido/proceso-compra";
        }

        if (pedidoCompraDto.getIdProducto() != null){
            Usuario usuario = usuarioService.findByUsername(userDetails.getUsername());
            Producto producto = productoService.findById(pedidoCompraDto.getIdProducto());
            pedido = PedidoUtilidades.crearPedidoConProducto(usuario, producto, pedidoCompraDto.getCantidad());

            ArticuloPedido articuloPedido = pedido.getArticulos().get(0);
            pedido.setArticulos(null);
            pedidoService.save(pedido);

            ArticuloPedidoPK id = new ArticuloPedidoPK(pedido.getId(), producto.getId());
            articuloPedido.setId(id);
            articuloPedidoService.save(articuloPedido);

        }else {
            // Creamos el carrito
            Pedido carrito = Pedido.builder()
                    .usuario(pedido.getUsuario())
                    .estado(Pedido.EstadoPedido.CARRITO)
                    .build();

            pedidoService.save(carrito);
        }

        Direccion direccion = direccionMapper.toEntity(pedidoCompraDto.getDireccion());
        Tarjeta tarjeta = tarjetaMapper.toEntity(pedidoCompraDto.getTarjeta());

        direccion.setPedido(pedido);
        tarjeta.setPedido(pedido);

        // Proceso de verificación de sí el pedido ya tiene una dirección o tarjeta asignada
        if (direccion.getId() != null && direccionService.existsById(direccion.getId())) {
            direccion = direccionService.update(direccion);
        } else {
            direccionService.save(direccion);
        }

        if (tarjeta.getId() != null && tarjetaService.existsById(tarjeta.getId())) {
            tarjeta = tarjetaService.update(tarjeta);
        } else {
            tarjetaService.save(tarjeta);
        }

        pedido.setDireccion(direccion);
        pedido.setTarjeta(tarjeta);
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);
        pedido.setFechaPedido(LocalDate.now());

        pedidoService.save(pedido);


        return "redirect:/index";
    }


}