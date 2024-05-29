package com.luislr.zerif.reposotirios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Preferencias;
import com.luislr.zerif.entidades.Usuario;
import com.luislr.zerif.repositorios.PreferenciasRepository;
import com.luislr.zerif.repositorios.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DBRider
public class PreferenciasRespositoryTest {
    @Autowired
    private PreferenciasRepository preferenciasRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml", "datasets/preferencias.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldFindPreferenciasById() {
        Optional<Preferencias> prederencias = preferenciasRepository.findById(1L);
        assertThat(prederencias).isPresent();
        assertThat(prederencias.get().getIdioma()).isEqualTo("es_ES");
    }

    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldCreatePreferencias() {
        Usuario usuario = usuarioRepository.findById(1L).orElseThrow();

        Preferencias preferencias = Preferencias.builder()
                .idioma("fr_FR")
                .usuario(usuario)
                .build();

        Preferencias savedPreferencias = preferenciasRepository.save(preferencias);
        assertThat(savedPreferencias).isNotNull();
        assertThat(savedPreferencias.getId()).isNotNull();
    }

    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml", "datasets/preferencias.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldUpdatePreferencias() {
        Preferencias preferencias = preferenciasRepository.findById(1L).orElseThrow();
        preferencias.setIdioma("de_DE");

        Preferencias updatedPreferencias = preferenciasRepository.save(preferencias);
        assertThat(updatedPreferencias.getIdioma()).isEqualTo("de_DE");
    }

    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml", "datasets/preferencias.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldDeletePreferencias() {
        preferenciasRepository.deleteById(1L);
        Optional<Preferencias> foundPreferencias = preferenciasRepository.findById(1L);
        assertThat(foundPreferencias).isNotPresent();
    }
}
