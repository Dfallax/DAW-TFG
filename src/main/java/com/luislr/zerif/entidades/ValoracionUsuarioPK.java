package com.luislr.zerif.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class ValoracionUsuarioPK implements Serializable {
    @Column(name = "id_usuario")
    private Long usuarioId;

    @Column(name = "id_producto")
    private Long productoId;
}
