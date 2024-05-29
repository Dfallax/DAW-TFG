package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    @Id
    @Column(name = "id_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String nombre;

    @DecimalMax(value = "999999.99")
    private double precio;

    @Column(length = 1000) // Define la longitud máxima como 500 caracteres
    private String descripcion;

    private String foto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<ValoracionProducto> valoraciones;

    private double mediaValoraciones = 0;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = true) //
    @JoinColumn(name = "id_subcategoria")
    private Subcategoria subcategoria;


}
