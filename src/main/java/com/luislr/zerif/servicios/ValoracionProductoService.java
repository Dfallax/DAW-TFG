package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.ValoracionProducto;
import com.luislr.zerif.repositorios.ValoracionProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValoracionProductoService {
    private final ValoracionProductoRepository valoracionProductoRepository;

    public void saveAll(List<ValoracionProducto> list){
        valoracionProductoRepository.saveAll(list);
    }

    public ValoracionProducto save(ValoracionProducto v) { return valoracionProductoRepository.save(v); }

    public List<ValoracionProducto> findAll() {
        return valoracionProductoRepository.findAll();
    }

    public List<ValoracionProducto> findByIdProducto(Producto producto){
        return valoracionProductoRepository.findById_ProductoId(producto);
    }

}
