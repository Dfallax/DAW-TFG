package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.Pedido;
import com.luislr.zerif.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByUsuarioAndEstado(Usuario usuario, Pedido.EstadoPedido estado);
    Optional<Pedido> findByEstado(Pedido.EstadoPedido estado);


}
