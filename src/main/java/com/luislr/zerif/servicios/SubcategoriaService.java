package com.luislr.zerif.servicios;


import com.luislr.zerif.entidades.Subcategoria;
import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.repositorios.SubcategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubcategoriaService {

    private final SubcategoriaRepository repositorio;

    public List<Subcategoria> findAll() {
        return repositorio.findAll();
    }

    public Subcategoria save(Subcategoria stp) { return repositorio.save(stp); }

    public void saveAll(List<Subcategoria> lista) {
        repositorio.saveAll(lista);
    }

    public Optional<Subcategoria> findById(Long id) {
        return repositorio.findById(id);
    }
    public Optional<Subcategoria> findByNombre(String nombre) {
        return repositorio.findByNombre(nombre);
    }

    public List<Subcategoria> findByCategoria(Categoria categoria) {
        return repositorio.findByCategoria(categoria);
    }

    public void delete(Subcategoria stp) {
        repositorio.delete(stp);
    }




}