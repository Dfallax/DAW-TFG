package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    @Id
    @Column(name = "id_pedido")
    @GeneratedValue
    private Long id;

    @Column(name = "fecha_pedido", updatable = false)
    @CreationTimestamp
    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Direccion direccion;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Tarjeta tarjeta;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<ArticuloPedido> articulos;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    public enum EstadoPedido {
        PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO, CARRITO
    }

    public BigDecimal getTotal() {
        if (articulos == null || articulos.isEmpty()) return BigDecimal.ZERO;

        BigDecimal total = articulos.stream()
                .map(ArticuloPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
