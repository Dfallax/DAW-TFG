package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Tarjeta;
import com.luislr.zerif.repositorios.TarjetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TarjetaService {
    private final TarjetaRepository tarjetaRepository;

    public void save(Tarjeta tarjeta) {
        tarjetaRepository.save(tarjeta);
    }

    public Tarjeta update(Tarjeta tarjeta) {
        return tarjetaRepository.saveAndFlush(tarjeta);
    }

    public boolean existsById(Long id) {
        return tarjetaRepository.existsById(id);
    }
}
