package com.luislr.zerif.dto.usuario;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioUpdateDto {

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    String username;

    @NotNull(message = "El nombre no puede ser nulo")
    String nombre;

    @NotNull(message = "Los apellidos no pueden ser nulos")
    String apellidos;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    String email;

    @NotNull(message = "El número de teléfono no puede ser nulo")
    String telefono;

}
