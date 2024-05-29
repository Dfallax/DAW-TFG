package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Direccion;
import com.luislr.zerif.repositorios.DireccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DireccionService {
    private final DireccionRepository direccionRepository;

    public void save(Direccion direccion) {
        direccionRepository.save(direccion);
    }

}
