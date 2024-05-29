package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.Subcategoria;
import com.luislr.zerif.repositorios.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public void saveAll(List<Producto> list){
        productoRepository.saveAll(list);
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> findAllRandom(){
        List<Producto> listaProducto = productoRepository.findAll();
        Collections.shuffle(listaProducto);
        return new ArrayList<>(listaProducto);
    }

    public Page<Producto> findAllPaginated(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    public Producto save(Producto p) { return productoRepository.save(p); }

    public Producto findById(Long id){
        return productoRepository.findById(id).orElse(null);
    }

    public Producto findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre).orElse(null);
    }

    public List<Producto> findByNombreContainsIgnoreCase(String nombre){
        return productoRepository.findByNombreContainsIgnoreCase(nombre);
    }

    public void delete(Producto m) {
        productoRepository.delete(m);
    }
    public List<Producto> findByCategoria(Categoria tipo){
        return productoRepository.findByCategoria(tipo);
    }
    public List<Producto> findByCategoriaAndSubcategoria(Categoria categoria, Subcategoria subcategoria){
        return productoRepository.findByCategoriaAndSubcategoria(categoria, subcategoria);
    }
}
