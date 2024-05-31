package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Categoria;
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

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DataSet(value = "datasets/categorias.yml", cleanBefore = true, cleanAfter = true)
    public void shouldFindCategoriaById(){
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(1L);
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
        Categoria categoria = categoriaRepository.findById(1L).orElseThrow();
        categoria.setNombre("Nombre actualizado");

        Categoria updateCategoria = categoriaRepository.save(categoria);
        assertThat(updateCategoria.getNombre()).isEqualTo("Nombre actualizado");
    }
    @Test
    @DataSet(value = "datasets/categorias.yml", cleanBefore = true, cleanAfter = true)
    public void shouldDeleteCategoria(){
        categoriaRepository.deleteById(1L);
        Categoria deleteedCategoria = categoriaRepository.findById(1L).orElse(null);

        assertNull(deleteedCategoria);
    }
}
