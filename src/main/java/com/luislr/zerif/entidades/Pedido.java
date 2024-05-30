package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_pedido", insertable = true, updatable = false)
    @CreationTimestamp
    private LocalDate fechPedido;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_direccion", referencedColumnName = "id_direccion")
    private Direccion direccion;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id_tarjeta")
    private Tarjeta tarjeta;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<ArticuloPedido> articulos;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;
    public static enum EstadoPedido{
        PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO, CARRITO;
    }

    public BigDecimal getTotal(){
        if (articulos == null || articulos.isEmpty()) return BigDecimal.ZERO;

        BigDecimal total = articulos.stream()
                .map(ArticuloPedido::getSubtotal)//  articulo -> articulo.getSubtotal()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
