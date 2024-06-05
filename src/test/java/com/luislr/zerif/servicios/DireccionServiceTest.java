package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Direccion;
import com.luislr.zerif.repositorios.DireccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionService direccionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        Direccion direccion = new Direccion();
        direccion.setId(1L);
        direccion.setCalle("Calle 1");

        when(direccionRepository.save(direccion)).thenReturn(direccion);

        direccionService.save(direccion);

        verify(direccionRepository, times(1)).save(direccion);
    }

    @Test
    public void testUpdate() {
        Direccion direccion = new Direccion();
        direccion.setId(1L);
        direccion.setCalle("Calle 1");

        when(direccionRepository.saveAndFlush(direccion)).thenReturn(direccion);

        Direccion updatedDireccion = direccionService.update(direccion);

        assertThat(updatedDireccion).isNotNull();
        assertThat(updatedDireccion.getCalle()).isEqualTo("Calle 1");
    }

    @Test
    public void testExistsById() {
        Long id = 1L;

        when(direccionRepository.existsById(id)).thenReturn(true);

        boolean exists = direccionService.existsById(id);

        assertThat(exists).isTrue();
    }

    @Test
    public void testSaveOrUpdate() {
        Direccion direccion = new Direccion();
        direccion.setId(1L);
        direccion.setCalle("Calle 1");

        when(direccionRepository.save(direccion)).thenReturn(direccion);

        direccionService.save(direccion);
        verify(direccionRepository, times(1)).save(direccion);

        when(direccionRepository.saveAndFlush(direccion)).thenReturn(direccion);

        Direccion updatedDireccion = direccionService.update(direccion);

        assertThat(updatedDireccion).isNotNull();
        assertThat(updatedDireccion.getCalle()).isEqualTo("Calle 1");
        verify(direccionRepository, times(1)).saveAndFlush(direccion);
    }
}
