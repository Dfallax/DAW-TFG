package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Categoria;
import com.luislr.zerif.repositorios.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoriaService {

    private final CategoriaRepository repositorio;

    public List<Categoria> findAll() {
        return repositorio.findAll();
    }

    public Categoria save(Categoria tp) { return repositorio.save(tp); }

    public void saveAll(List<Categoria> lista) {
        repositorio.saveAll(lista);
    }

    public Optional<Categoria> findById(Long id) {
        return repositorio.findById(id);
    }

    public void delete(Categoria tp) {
        repositorio.delete(tp);
    }
    public Optional<Categoria> findByNombre(String nombre) {
        return repositorio.findByNombre(nombre);
    }
}
