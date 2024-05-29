package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    public Optional<Perfil> findByNombre(String nombre);


}
