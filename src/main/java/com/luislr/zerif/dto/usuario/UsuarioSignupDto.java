package com.luislr.zerif.dto.usuario;

import com.luislr.zerif.entidades.Perfil;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record UsuarioSignupDto(
  @NotNull
  String username,
  @NotNull
  String email,
  @NotNull
  String password,
  Set<Perfil> perfiles
){}
