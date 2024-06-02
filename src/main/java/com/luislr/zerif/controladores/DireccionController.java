package com.luislr.zerif.controladores;

import com.luislr.zerif.dto.direccion.DireccionMapper;
import com.luislr.zerif.servicios.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/direccion")
public class DireccionController {

    private final PedidoService pedidoService;
    private final DireccionMapper mapper;
/*
    @GetMapping("/compra/new")
    public String compraForm(Model model){
        model.addAttribute("mascotaDto", new Mascota());
    }


    @PostMapping("/compra/new/submit")
    public String direccionCarrito(@Valid @ModelAttribute("direccion") Dire tarjetaCreateDto,
                                    BindingResult bindingResult,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                    Model model) {
        String username = userDetails.getUsername();
        Pedido carrito = pedidoService.obtenerCarrito(username);

        if (bindingResult.hasErrors()) {
            log.info("Hay errores en el formulario");
            bindingResult.getFieldErrors();
            return "direccion-compra";
        } else {
            Tarjeta tarjeta = TarjetaUtilidades.convertToEntityCompra(tarjetaCreateDto);
            carrito.setTarjeta(tarjeta);
            model.addAttribute("carrito", carrito);
            model.addAttribute("tarjeta", tarjetaCreateDto);
            model.addAttribute("direccion", DireccionUtilidades.convertToDto(carrito.getDireccion()));
        }
        return "redirect:/tarjeta/compra/new";

    }*/
}
