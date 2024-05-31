package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "direccion")
public class Direccion {
    @Id
    @Column(name = "id_direccion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;

    private int numero;

    private int piso;

    private char puerta;

    private String ciudad;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
