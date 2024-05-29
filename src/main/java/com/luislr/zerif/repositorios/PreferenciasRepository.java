package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.Preferencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenciasRepository extends JpaRepository<Preferencias, Long> {

  @PostAuthorize("returnObject != null and returnObject.usuario.username == authentication.name")
  Optional<Preferencias> findById(Long id);

  @Query("select p from #{#entityName} p where p.usuario.username = ?#{authentication.name}")
  Optional<Preferencias> findUsuarioPreferencias();
}