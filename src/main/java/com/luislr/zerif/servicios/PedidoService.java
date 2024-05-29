package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.ArticuloPedidoPK;
import com.luislr.zerif.entidades.Pedido;
import com.luislr.zerif.entidades.Usuario;
import com.luislr.zerif.repositorios.ArticuloPedidoRepository;
import com.luislr.zerif.repositorios.PedidoRepository;
import com.luislr.zerif.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioService usuarioService;

    private final ArticuloPedidoRepository articuloPedidoRepository;

    public void save(Pedido pedido) {
        pedidoRepository.save(pedido);
    }
    @Transactional
    public void eliminarArticulo(ArticuloPedidoPK id) {
        articuloPedidoRepository.deleteById(id);
    }

    public Pedido obtenerCarrito(String username) {
        Usuario usuarioActual = usuarioService.findByUsername(username);
        return pedidoRepository.findByUsuarioAndEstado(usuarioActual, Pedido.EstadoPedido.CARRITO)
                .orElseThrow(() -> new RuntimeException("No se encontró el carrito para el usuario actual"));
    }

    public Pedido findCarritoByUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuarioAndEstado(usuario, Pedido.EstadoPedido.CARRITO).orElse(null);
    }

}
