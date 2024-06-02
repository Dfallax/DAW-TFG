package com.luislr.zerif.controladores;

import com.luislr.zerif.dto.direccion.DireccionMapper;
import com.luislr.zerif.dto.tarjeta.TarjetaMapper;
import com.luislr.zerif.servicios.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/tarjeta")
public class TarjetaController {

    private final PedidoService pedidoService;
    private final TarjetaMapper tarjetaMapper;
    private final DireccionMapper direccionMapper;
/*
    @PostMapping("/form/compra")
    public String pedidoFromCarrito(@Valid @ModelAttribute("tarjeta") TarjetaCreateDto tarjetaCreateDto,
                                     BindingResult bindingResult,
                                     Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pedido carrito = pedidoService.obtenerCarrito(username);

        if (bindingResult.hasErrors()) {
            log.info("Hay errores en el formulario");
            bindingResult.getFieldErrors();
            return "tarjeta-compra";
        } else {
            Tarjeta tarjeta = TarjetaUtilidades.convertToEntityCompra(tarjetaCreateDto);
            carrito.setTarjeta(tarjeta);
            model.addAttribute("carrito", carrito);
            model.addAttribute("tarjeta", tarjetaCreateDto);
            model.addAttribute("direccion", DireccionUtilidades.convertToDto(carrito.getDireccion()));
        }
        return "pedido/proceso-compra";

    }*/
}
