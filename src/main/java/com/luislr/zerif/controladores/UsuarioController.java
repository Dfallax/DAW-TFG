package com.luislr.zerif.controladores;
import com.luislr.zerif.dto.PerfilSignupDto;
import com.luislr.zerif.dto.UsuarioSignupDto;
import com.luislr.zerif.entidades.Usuario;
import com.luislr.zerif.servicios.PerfilService;
import com.luislr.zerif.servicios.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

  private final UsuarioService usuarioService;
  private final PerfilService perfilService;


  @GetMapping("/login")
  public String login(){
    return "usuario/login";
  }

  @GetMapping("/logout")
  public String logout(){
    return "/index";
  }

  @GetMapping("/signup")
  public String signup(Model model){
    model.addAttribute("usuarioSignupDto", UsuarioSignupDto.builder().build());
    return "usuario/signup";
  }

  @GetMapping("/miperfil")
  public String mipefil(@AuthenticationPrincipal UserDetails userDetails,
                        Model model
  ){
    Usuario usuario = usuarioService.findByUsername(userDetails.getUsername());
    model.addAttribute("usuario", usuario);
    return "usuario/mi-perfil";
  }

  @PostMapping("/signup")
  public String signupSubmit(@Valid @ModelAttribute("usuarioSignupDto") UsuarioSignupDto dto,
                                   BindingResult bindingResult,
                                   Model model) {
    if (bindingResult.hasErrors()) {
      log.info("hay errores en el formulario");
      bindingResult.getFieldErrors()
              .forEach(e -> log.info("field: " + e.getField() + ", rejected value: " + e.getRejectedValue()));
      return "usuario/form";
    } else {
      Usuario usuario = usuarioService.findByUsernameOrEmail(dto.username(), dto.email());
      if (usuario != null) { // el usuario ya existe
        bindingResult.rejectValue("username", "username.existente",
                "ya existe un usuario con ese username");
        return "usuario/signup";
      }

      usuarioService.save(dto);
      return "redirect:/usuario/login";

    }
  }

}
