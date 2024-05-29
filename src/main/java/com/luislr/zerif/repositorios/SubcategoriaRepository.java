package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.Subcategoria;
import com.luislr.zerif.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria,Long> {
    public Optional<Subcategoria> findByNombre(String nombre);

    public List<Subcategoria> findByCategoria(Categoria categoria);
}

