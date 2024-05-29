package com.luislr.zerif.controladores;

import com.luislr.zerif.entidades.SeccionBlog;
import com.luislr.zerif.servicios.SeccionBlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/blog")
public class BlogController {

    private final SeccionBlogService seccionBlogService;

    @GetMapping("/lista/filtrada")
    public String listadoFiltrado(@RequestParam("titulo") String titulo, Model model) {
        List<SeccionBlog> seccionBlogList = seccionBlogService.findByTituloContainsIgnoreCase(titulo);
        if (titulo.isEmpty()) {
            seccionBlogList = seccionBlogService.findAll();
        }
        model.addAttribute("listaSeccionBlog", seccionBlogList);
        return "fragmentos/seccion-blog::seccion-blog";
    }


    @GetMapping("/pagina")
    public String ubicaciones(Model model){
        List<SeccionBlog> listaSeccionBlog = seccionBlogService.findAll();
        model.addAttribute("listaSeccionBlog", listaSeccionBlog);
        return "blog/pagina-blog";
    }

}
