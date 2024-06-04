package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Direccion;
import com.luislr.zerif.entidades.Pedido;
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
    @Autowired
    private PedidoRepository pedidoRepository;

    private static final Long ID_DIRECCION = 1L;
    private static final Long ID_PEDIDO = 1L;


    @Test
    @DataSet(value = {"datasets/direcciones.yml", "datasets/pedidos.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldFindDireccionById(){
        Optional<Direccion> direccionOptional = direccionRepository.findById(ID_DIRECCION);
        assertThat(direccionOptional).isPresent();
    }
    @Test
    @DataSet(value = {"datasets/pedidos.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldCreateDireccion(){
        Pedido pedido = pedidoRepository.getReferenceById(ID_PEDIDO);
        Direccion direccion = Direccion.builder()
                .calle("Nueva calle")
                .numero(123)
                .piso(2)
                .pedido(pedido)
                .build();
        Direccion savedDireccion = direccionRepository.save(direccion);
        assertNotNull(savedDireccion.getId());
    }
    @Test
    @DataSet(value = {"datasets/direcciones.yml", "datasets/pedidos.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldUpdateDireccion(){
        Direccion direccion = direccionRepository.findById(ID_DIRECCION).orElseThrow();
        direccion.setCalle("Calle actualizada");

        Direccion updateDirecion = direccionRepository.save(direccion);
        assertThat(updateDirecion.getCalle()).isEqualTo("Calle actualizada");
    }
    @Test
    @DataSet(value = {"datasets/direcciones.yml", "datasets/pedidos.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldDeleteDireccion(){
        direccionRepository.deleteById(1L);
        Direccion deletedDireccion = direccionRepository.findById(ID_DIRECCION).orElse(null);

        assertNull(deletedDireccion);
    }
}
