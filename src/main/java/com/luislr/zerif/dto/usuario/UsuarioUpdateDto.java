package com.luislr.zerif.dto.usuario;

import com.luislr.zerif.entidades.Perfil;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public class UsuarioUpdateDto {
    @NotNull
    String username;
    @NotNull
    String nombre;
    @NotNull
    String apellidos;
    @NotNull
    String email;
    @NotNull
    String telefono;
    @NotNull
    String password;
}
