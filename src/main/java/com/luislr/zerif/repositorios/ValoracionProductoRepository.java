package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.ValoracionProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValoracionProductoRepository extends JpaRepository<ValoracionProducto, Long> {
    public List<ValoracionProducto> findById_ProductoId(Producto producto);
}
