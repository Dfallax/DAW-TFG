package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Pedido;
import com.luislr.zerif.entidades.Usuario;
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
public class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    private static final Long ID_PEDIDO = 1L;
    private static final Long ID_USUARIO = 1L;


    @Test
    @DataSet(value = {"datasets/pedidos.yml",
            "datasets/tarjetas.yml", "datasets/direcciones.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
            "datasets/preferencias.yml"
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldFindPedidoById(){
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(ID_PEDIDO);
        assertThat(pedidoOptional).isPresent();
    }

    @Test
    @DataSet(value = {"datasets/preferencias.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml"
    }
            , cleanBefore = true, cleanAfter = true)
    public void shouldCreatePedido(){
        Usuario usuario = usuarioRepository.findById(ID_USUARIO).orElseThrow();

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .estado(Pedido.EstadoPedido.PENDIENTE)
                .build();

        Pedido savedPedido = pedidoRepository.save(pedido);
        assertNotNull(savedPedido.getId());
    }

    @Test
    @DataSet(value = {"datasets/pedidos.yml",
            "datasets/tarjetas.yml", "datasets/direcciones.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
            "datasets/preferencias.yml"
    }
    , cleanBefore = true, cleanAfter = true)
    public void shouldUpdatePedido(){
        Pedido pedido = pedidoRepository.findById(ID_PEDIDO).orElseThrow();
        pedido.setEstado(Pedido.EstadoPedido.COMPLETADO);

        Pedido updatedPedido = pedidoRepository.save(pedido);
        assertThat(updatedPedido.getEstado()).isEqualTo(Pedido.EstadoPedido.COMPLETADO);
    }

    @Test
    @DataSet(value = {"datasets/pedidos.yml",
            "datasets/tarjetas.yml", "datasets/direcciones.yml",
            "datasets/usuarios.yml", "datasets/perfiles.yml",
            "datasets/preferencias.yml"
    },
            cleanBefore = true, cleanAfter = true)
    public void shouldDeletePedido(){
        pedidoRepository.deleteById(1L);
        Pedido deletedPedido = pedidoRepository.findById(ID_PEDIDO).orElse(null);

        assertNull(deletedPedido);
    }
}
