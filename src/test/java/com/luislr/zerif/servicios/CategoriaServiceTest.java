package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.repositorios.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    public CategoriaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoria 1");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Optional<Categoria> foundCategoria = categoriaService.findById(1L);

        assertThat(foundCategoria).isPresent();
        assertThat(foundCategoria.get().getNombre()).isEqualTo("Categoria 1");
    }

    @Test
    public void testFindByNombre() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoria 1");

        when(categoriaRepository.findByNombre("Categoria 1")).thenReturn(Optional.of(categoria));

        Optional<Categoria> foundCategoria = categoriaService.findByNombre("Categoria 1");

        assertThat(foundCategoria).isPresent();
        assertThat(foundCategoria.get().getNombre()).isEqualTo("Categoria 1");
    }

    @Test
    public void testFindAll() {
        Categoria categoria1 = new Categoria();
        categoria1.setNombre("Categoria 1");

        Categoria categoria2 = new Categoria();
        categoria2.setNombre("Categoria 2");

        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);

        when(categoriaRepository.findAll()).thenReturn(categorias);

        List<Categoria> foundCategorias = categoriaService.findAll();

        assertThat(foundCategorias).hasSize(2).contains(categoria1, categoria2);
    }

    @Test
    public void testCreateCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Nueva Categoria");

        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria createdCategoria = categoriaService.save(categoria);

        assertThat(createdCategoria.getNombre()).isEqualTo("Nueva Categoria");
    }

    @Test
    public void testSaveAll() {
        Categoria categoria1 = new Categoria();
        categoria1.setNombre("Categoria 1");

        Categoria categoria2 = new Categoria();
        categoria2.setNombre("Categoria 2");

        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);

        when(categoriaRepository.saveAll(categorias)).thenReturn(categorias);

        categoriaService.saveAll(categorias);

        verify(categoriaRepository, times(1)).saveAll(categorias);
    }

    @Test
    public void testUpdateCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoria 1");

        Categoria updatedCategoriaDetails = new Categoria();
        updatedCategoriaDetails.setNombre("Nombre actualizado");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria updatedCategoria = categoriaService.update(1L, updatedCategoriaDetails);

        assertThat(updatedCategoria.getNombre()).isEqualTo("Nombre actualizado");
    }

    @Test
    public void testDeleteCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoria 1");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).delete(categoria);

        categoriaService.delete(1L);

        verify(categoriaRepository, times(1)).delete(categoria);
    }

    @Test
    public void testFindByIdNotFound() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Categoria> foundCategoria = categoriaService.findById(1L);

        assertThat(foundCategoria).isNotPresent();
    }

    @Test
    public void testFindByNombreNotFound() {
        when(categoriaRepository.findByNombre("Categoria 1")).thenReturn(Optional.empty());

        Optional<Categoria> foundCategoria = categoriaService.findByNombre("Categoria 1");

        assertThat(foundCategoria).isNotPresent();
    }

    @Test
    public void testDeleteCategoriaNotFound() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            categoriaService.delete(1L);
        });
    }
}
