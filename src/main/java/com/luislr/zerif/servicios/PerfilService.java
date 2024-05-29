package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Perfil;
import com.luislr.zerif.repositorios.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PerfilService {

    private final PerfilRepository repositorio;
    public Perfil save(Perfil p) { return repositorio.save(p); }

}
