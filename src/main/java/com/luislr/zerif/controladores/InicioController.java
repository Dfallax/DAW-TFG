package com.luislr.zerif.controladores;

import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.ValoracionProducto;
import com.luislr.zerif.servicios.ProductoService;
import com.luislr.zerif.storage.StorageService;
import com.luislr.zerif.utilidades.ProductoUtilidades;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class InicioController {

    private final ProductoService productoService;
    private final StorageService storageService;

    @GetMapping({"","/index"})
    public String inicio(HttpServletRequest request, HttpServletResponse response, Model model,
        @RequestParam(name = "opción", required = false, defaultValue = "0") int opcion) {
        List<Producto> listaProducto = productoService.findAllRandom();
        ProductoUtilidades.calcularMediaValoracion(listaProducto);
        model.addAttribute("listaProducto", listaProducto.subList(0, 6));
        return "index";
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }

    @GetMapping("ubicaciones")
    public String ubicaciones(){
        return "ubicaciones";
    }
}
