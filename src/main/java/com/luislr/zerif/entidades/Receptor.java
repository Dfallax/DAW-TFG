package com.luislr.zerif.entidades;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "receptor")
public class Receptor {
    @Id
    @Column(name = "id_receptor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    private String telefono;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
