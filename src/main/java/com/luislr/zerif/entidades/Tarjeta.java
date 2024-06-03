package com.luislr.zerif.entidades;

import com.luislr.zerif.utilidades.YearMonthAttributeConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.YearMonth;

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
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @DateTimeFormat(pattern = "MM/yyyy")
    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth fechCaducidad;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public YearMonth getFechCaducidad() {
        return fechCaducidad;
    }

    public void setFechCaducidad(YearMonth fechCaducidad) {
        this.fechCaducidad = fechCaducidad;
    }
}
