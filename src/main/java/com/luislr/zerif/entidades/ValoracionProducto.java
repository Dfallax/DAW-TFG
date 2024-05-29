package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import java.io.Serializable;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "valoracion_producto_usuario")
public class ValoracionProducto implements Serializable {

    @EmbeddedId
    private ValoracionUsuarioPK id;

    @ManyToOne
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    @MapsId("productoId")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    @MapsId("usuarioId")
    private Usuario usuario;

    @Column(scale = 0)
    @Min(value = 0, message = "La valoración mínima es 0")
    @Max(value = 10, message = "La valoración máxima es 10")
    private int valoracion;

}
