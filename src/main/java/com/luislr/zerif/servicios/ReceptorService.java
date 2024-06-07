package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Receptor;
import com.luislr.zerif.repositorios.ReceptorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceptorService {

    private final ReceptorRepository receptorRepository;

    public void save(Receptor receptor) {
        receptorRepository.save(receptor);
    }

    public Receptor update(Receptor receptor) {
        return receptorRepository.saveAndFlush(receptor);
    }

    public boolean existsById(Long id) {
        return receptorRepository.existsById(id);
    }
}
