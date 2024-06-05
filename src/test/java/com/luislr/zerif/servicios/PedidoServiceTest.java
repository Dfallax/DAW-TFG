package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.ArticuloPedidoPK;
import com.luislr.zerif.entidades.Pedido;
import com.luislr.zerif.entidades.Usuario;
import com.luislr.zerif.repositorios.ArticuloPedidoRepository;
import com.luislr.zerif.repositorios.PedidoRepository;
import com.luislr.zerif.repositorios.UsuarioRepository;
import com.luislr.zerif.dto.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ArticuloPedidoRepository articuloPedidoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioMapper usuarioMapper;

    private UsuarioService usuarioService;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Crear instancia manualmente de UsuarioService con sus dependencias
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder, usuarioMapper);
        // Inyectar manualmente UsuarioService en PedidoService
        pedidoService = new PedidoService(pedidoRepository, usuarioService, articuloPedidoRepository);
    }

    @Test
    public void testSave() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido savedPedido = pedidoService.save(pedido);

        assertThat(savedPedido).isEqualTo(pedido);
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    public void testEliminarArticulo() {
        ArticuloPedidoPK id = new ArticuloPedidoPK();
        doNothing().when(articuloPedidoRepository).deleteById(id);

        pedidoService.eliminarArticulo(id);

        verify(articuloPedidoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testObtenerCarrito() {
        String username = "user";
        Usuario usuario = new Usuario();

        Pedido carrito = new Pedido();
        when(Optional.of( usuarioService.findByUsername(username))).thenReturn(Optional.of(usuario));
        when(pedidoRepository.findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO)).thenReturn(Optional.of(carrito));

        Pedido foundCarrito = pedidoService.obtenerCarrito(username);

        assertThat(foundCarrito).isEqualTo(carrito);
        verify(usuarioService, times(1)).findByUsername(username);
        verify(pedidoRepository, times(1)).findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO);
    }

    @Test
    public void testObtenerCarritoNotFound() {
        String username = "user";
        Usuario usuario = new Usuario();
        when(usuarioService.findByUsername(username)).thenReturn(usuario);
        when(pedidoRepository.findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pedidoService.obtenerCarrito(username));
        verify(usuarioService, times(1)).findByUsername(username);
        verify(pedidoRepository, times(1)).findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO);
    }

    @Test
    public void testFindCarritoByUsuario() {
        Usuario usuario = new Usuario();
        Pedido carrito = new Pedido();
        when(pedidoRepository.findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO)).thenReturn(Optional.of(carrito));

        Pedido foundCarrito = pedidoService.findCarritoByUsuario(usuario);

        assertThat(foundCarrito).isEqualTo(carrito);
        verify(pedidoRepository, times(1)).findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO);
    }

    @Test
    public void testFindCarritoByUsuarioNotFound() {
        Usuario usuario = new Usuario();
        when(pedidoRepository.findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO)).thenReturn(Optional.empty());

        Pedido foundCarrito = pedidoService.findCarritoByUsuario(usuario);

        assertThat(foundCarrito).isNull();
        verify(pedidoRepository, times(1)).findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO);
    }
}
