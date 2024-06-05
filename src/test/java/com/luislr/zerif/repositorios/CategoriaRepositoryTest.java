package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Categoria;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@DataJpaTest
@DBRider
public class CategoriaRepositoryTest {

    private static final Long ID_CATEGORIA = 1L;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DataSet(value = "datasets/categorias.yml", cleanBefore = true, cleanAfter = true)
    public void shouldFindCategoriaById(){
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(ID_CATEGORIA);
        assertThat(categoriaOptional).isPresent();
    }
    @Test
    public void shouldCreateCategoria(){
        Categoria categoria = Categoria.builder()
                .nombre("Nueva Categoria")
                .build();
        Categoria savedCategoria = categoriaRepository.save(categoria);
        assertNotNull(savedCategoria.getId());
    }
    @Test
    @DataSet(value = "datasets/categorias.yml", cleanBefore = true, cleanAfter = true)
    public void shouldUpdateCategoria(){
        Categoria categoria = categoriaRepository.findById(ID_CATEGORIA).orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada con el ID 1L"));
        categoria.setNombre("Nombre actualizado");

        Categoria updateCategoria = categoriaRepository.save(categoria);
        assertThat(updateCategoria.getNombre()).isEqualTo("Nombre actualizado");
    }
    @Test
    @DataSet(value = "datasets/categorias.yml", cleanBefore = true, cleanAfter = true)
    public void shouldDeleteCategoria(){
        categoriaRepository.deleteById(ID_CATEGORIA);
        Categoria deleteedCategoria = categoriaRepository.findById(ID_CATEGORIA).orElse(null);

        assertNull(deleteedCategoria);
    }
}
