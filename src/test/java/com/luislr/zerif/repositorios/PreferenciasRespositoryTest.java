package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Preferencias;
import com.luislr.zerif.entidades.Usuario;
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

    private static final Long ID_USUARIO = 1L;
    private static final Long ID_PREFERENCIAS = 1L;


    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml", "datasets/preferencias.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldFindPreferenciasById() {
        Optional<Preferencias> prederencias = preferenciasRepository.findById(ID_PREFERENCIAS);
        assertThat(prederencias).isPresent();
    }

    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldCreatePreferencias() {
        Usuario usuario = usuarioRepository.findById(ID_USUARIO).orElseThrow();

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
        Preferencias preferencias = preferenciasRepository.findById(ID_PREFERENCIAS).orElseThrow();
        preferencias.setIdioma("de_DE");

        Preferencias updatedPreferencias = preferenciasRepository.save(preferencias);
        assertThat(updatedPreferencias.getIdioma()).isEqualTo("de_DE");
    }

    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml", "datasets/preferencias.yml"},
            cleanBefore = true, cleanAfter = true)
    public void shouldDeletePreferencias() {
        preferenciasRepository.deleteById(ID_PREFERENCIAS);
        Optional<Preferencias> foundPreferencias = preferenciasRepository.findById(ID_PREFERENCIAS);
        assertThat(foundPreferencias).isNotPresent();
    }
}
