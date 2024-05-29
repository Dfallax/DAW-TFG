package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.ArticuloPedido;
import com.luislr.zerif.entidades.ArticuloPedidoPK;
import com.luislr.zerif.repositorios.ArticuloPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticuloPedidoService {
    private final ArticuloPedidoRepository articuloPedidoRepository;

    public void save(ArticuloPedido articuloPedido) {
        articuloPedidoRepository.save(articuloPedido);
    }

    public Optional<ArticuloPedido> findById(ArticuloPedidoPK articuloPedidoPK){
        return articuloPedidoRepository.findById(articuloPedidoPK);
    }

    public void deleteArticuloPedido(ArticuloPedido articuloPedido){
        articuloPedidoRepository.delete(articuloPedido);
    }
}
