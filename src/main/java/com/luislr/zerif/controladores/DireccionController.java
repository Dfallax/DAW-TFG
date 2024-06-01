package com.luislr.zerif.controladores;

import com.luislr.zerif.dto.direccion.DireccionCompraDto;
import com.luislr.zerif.entidades.Direccion;
import com.luislr.zerif.entidades.Pedido;
import com.luislr.zerif.servicios.PedidoService;
import com.luislr.zerif.utilidades.DireccionUtilidades;
import com.luislr.zerif.utilidades.TarjetaUtilidades;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/direccion")
public class DireccionController {

    private final PedidoService pedidoService;

    @PostMapping("/compra/new")
    public String pedidoFromCarrito(@Valid @ModelAttribute("direccion") DireccionCompraDto direccionCompraDto,
                                    BindingResult bindingResult,
                                    Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pedido carrito = pedidoService.obtenerCarrito(username);
        if (bindingResult.hasErrors()) {
            log.info("Hay errores en el formulario");
            bindingResult.getFieldErrors()
                    .forEach(e -> log.info("field: " + e.getField() + ", rejected value: " + e.getRejectedValue()));
        } else {
            Direccion direccion = DireccionUtilidades.convertToEntity(direccionCompraDto);
            carrito.setDireccion(direccion);
        }
        model.addAttribute("carrito", carrito);
        model.addAttribute("direccion",DireccionUtilidades.convertToDto(carrito.getDireccion()));
        model.addAttribute("tarjeta", TarjetaUtilidades.convertToDtoCompra(carrito.getTarjeta()));

        return "pedido/proceso-compra";
    }
}
