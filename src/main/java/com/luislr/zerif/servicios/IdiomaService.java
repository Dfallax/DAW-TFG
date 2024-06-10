package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Idioma;
import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.repositorios.IdiomaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdiomaService {
    private final IdiomaRepository idiomaRepository;
    public void saveAll(List<Idioma> list){
        idiomaRepository.saveAll(list);
    }

    public Optional<Idioma> findById(Long id){
        return idiomaRepository.findById(id);
    }
}
