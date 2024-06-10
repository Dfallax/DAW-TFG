package com.luislr.zerif.dto.perfil;

import jakarta.validation.constraints.NotNull;


public record PerfilSignupDto(
  Long id,
  @NotNull
  String nombre
  ) {}
