package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Pedido;
import com.luislr.zerif.entidades.Tarjeta;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@DataJpaTest
@DBRider
public class TarjetaRepositoryTest {

    @Autowired
    private TarjetaRepository tarjetaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    @DataSet(value = {"datasets/tarjetas.yml", "datasets/pedidos.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldFindTarjetaById(){
        Optional<Tarjeta> direccionOptional = tarjetaRepository.findById(1L);
        assertThat(direccionOptional).isPresent();
    }
    @Test
    @DataSet(value = {"datasets/tarjetas.yml", "datasets/pedidos.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldCreateTarjeta(){
        Pedido pedido = pedidoRepository.getReferenceById(1L);
        Tarjeta tarjeta = Tarjeta.builder()
                .numTarjeta("12338363662")
                .cv("023")
                .fechCaducidad(LocalDate.now())
                .pedido(pedido)
                .build();
        Tarjeta savedTarjeta = tarjetaRepository.save(tarjeta);
        assertNotNull(savedTarjeta.getId());
    }
    @Test
    @DataSet(value = {"datasets/tarjetas.yml", "datasets/pedidos.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldUpdateTarjeta(){
        Tarjeta tarjeta = tarjetaRepository.findById(1L).orElseThrow();
        tarjeta.setFechCaducidad(LocalDate.now().minusYears(1));

        Tarjeta updateTarjete = tarjetaRepository.save(tarjeta);
        assertThat(updateTarjete.getFechCaducidad()).isEqualTo(LocalDate.now().minusYears(1));
    }
    @Test
    @DataSet(value = {"datasets/tarjetas.yml", "datasets/pedidos.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldDeleteTarjeta(){
        tarjetaRepository.deleteById(1L);
        Tarjeta deletedTarjeta = tarjetaRepository.findById(1L).orElse(null);

        assertNull(deletedTarjeta);
    }
}
