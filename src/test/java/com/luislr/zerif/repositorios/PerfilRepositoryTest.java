package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Perfil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DBRider
public class PerfilRepositoryTest {

    @Autowired
    private PerfilRepository perfilRepository;

    private static final Long ID_PERFIL = 1L;

    @Test
    @DataSet(value = "datasets/perfiles.yml", cleanBefore = true, cleanAfter = true)
    public void shouldFindPerfilById() {
        Optional<Perfil> perfilOptional = perfilRepository.findById(ID_PERFIL);
        assertThat(perfilOptional).isPresent();
    }

    @Test
    public void shouldCreatePerfil() {
        Perfil perfil = Perfil.builder()
                .nombre("EMPLEADO")
                .build();

        Perfil savedPerfil = perfilRepository.save(perfil);
        assertThat(savedPerfil).isNotNull();
        assertThat(savedPerfil.getId()).isNotNull();
        assertThat(savedPerfil.getNombre()).isEqualTo("EMPLEADO");
    }

    @Test
    @DataSet(value = {"datasets/perfiles.yml"}, cleanBefore = true, cleanAfter = true)
    public void shouldUpdatePerfil() {
        Perfil perfil = perfilRepository.findById(ID_PERFIL).orElseThrow();
        perfil.setNombre("MODERATOR");

        Perfil updatedPerfil = perfilRepository.save(perfil);
        assertThat(updatedPerfil.getNombre()).isEqualTo("MODERATOR");
    }

    @Test
    @DataSet(value = {"datasets/perfiles.yml"}, cleanBefore = true, cleanAfter = true)
    public void shouldDeletePerfil() {
        perfilRepository.deleteById(1L);
        Optional<Perfil> foundPerfil = perfilRepository.findById(ID_PERFIL);
        assertThat(foundPerfil).isNotPresent();
    }
}
