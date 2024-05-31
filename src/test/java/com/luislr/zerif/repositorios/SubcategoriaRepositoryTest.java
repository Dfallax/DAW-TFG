package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.entidades.Subcategoria;
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
public class SubcategoriaRepositoryTest {

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DataSet(value = {"datasets/subcategorias.yml", "datasets/categorias.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldFindSubcategoriaById(){
        Optional<Subcategoria> subcategoriaOptional = subcategoriaRepository.findById(1L);
        assertThat(subcategoriaOptional).isPresent();
    }
    @Test
    @DataSet(value = "datasets/categorias.yml", cleanBefore = true, cleanAfter = true)
    public void shouldCreateSubcategoria(){
        Categoria categoriaOptional = categoriaRepository.findById(1L).orElseThrow();

        Subcategoria subcategoria = Subcategoria.builder()
                .nombre("Nueva Subcategoria")
                .categoria(categoriaOptional)
                .build();
        Subcategoria savedSubcategoria = subcategoriaRepository.save(subcategoria);
        assertNotNull(savedSubcategoria.getId());
    }
    @Test
    @DataSet(value = {"datasets/subcategorias.yml", "datasets/categorias.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldUpdateSubcategoria(){
        Subcategoria subcategoria = subcategoriaRepository.findById(1L).orElseThrow();
        subcategoria.setNombre("Nombre actualizado");

        Subcategoria updateSubcategoria = subcategoriaRepository.save(subcategoria);
        assertThat(updateSubcategoria.getNombre()).isEqualTo("Nombre actualizado");
    }
    @Test
    @DataSet(value = {"datasets/subcategorias.yml", "datasets/categorias.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldDeleteSubcategoria(){
        subcategoriaRepository.deleteById(1L);
        Subcategoria deletedSubcategoria = subcategoriaRepository.findById(1L).orElse(null);

        assertNull(deletedSubcategoria);
    }
}
