package com.luislr.zerif.dto;

import jakarta.validation.constraints.NotNull;


public record PerfilSignupDto(
  Long id,
  @NotNull
  String nombre
  ) {}
