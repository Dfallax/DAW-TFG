package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tarjeta")
public class Tarjeta {
    @Id
    @Column(name = "id_tarjeta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_tarjeta")
    private String numTarjeta;

    private String cv;
    @Column(name = "fecha_caducidad")
    private LocalDate fechCaducidad;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
