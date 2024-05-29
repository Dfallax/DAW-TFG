package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    public Optional<Producto> findByNombre(String nombre);
    public List<Producto> findByNombreContainsIgnoreCase(String filtro);
    public List<Producto> findByCategoria(Categoria tipo);

    public List<Producto> findByCategoriaAndSubcategoria(Categoria categoria, Subcategoria subcategoria);

}
