package com.luislr.zerif.reposotirios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.entidades.Direccion;
import com.luislr.zerif.repositorios.DireccionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@DataJpaTest
@DBRider
public class DireccionRepositoryTest {

    @Autowired
    private DireccionRepository direccionRepository;

    @Test
    @DataSet(value = "datasets/direcciones.yml", cleanBefore = true, cleanAfter = true)
    public void shouldFindDireccionById(){
        Optional<Direccion> direccionOptional = direccionRepository.findById(1L);
        assertThat(direccionOptional).isPresent();
    }
    @Test
    public void shouldCreateDireccion(){
        Direccion direccion = Direccion.builder()
                .calle("Nueva calle")
                .numero(123)
                .piso(2)
                .build();
        Direccion savedDireccion = direccionRepository.save(direccion);
        assertNotNull(savedDireccion.getId());
    }
    @Test
    @DataSet(value = "datasets/direcciones.yml", cleanBefore = true, cleanAfter = true)
    public void shouldUpdateCategoria(){
        Direccion direccion = direccionRepository.findById(1L).orElseThrow();
        direccion.setCalle("Calle actualizada");

        Direccion updateDirecion = direccionRepository.save(direccion);
        assertThat(updateDirecion.getCalle()).isEqualTo("Calle actualizada");
    }
    @Test
    @DataSet(value = "datasets/direcciones.yml", cleanBefore = true, cleanAfter = true)
    public void shouldDeleteCategoria(){
        direccionRepository.deleteById(1L);
        Direccion deletedDireccion = direccionRepository.findById(1L).orElse(null);

        assertNull(deletedDireccion);
    }
}
