package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.ArticuloPedido;
import com.luislr.zerif.entidades.ArticuloPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticuloPedidoRepository extends JpaRepository<ArticuloPedido, ArticuloPedidoPK> {
    public Optional<ArticuloPedido> findById(ArticuloPedidoPK id);
}
