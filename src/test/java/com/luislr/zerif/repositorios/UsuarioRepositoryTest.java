package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.Perfil;
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
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml"})
    public void shouldFindUsuarioById() {
        Optional<Usuario> usuario = usuarioRepository.findById(1L);
        assertThat(usuario).isPresent();
        assertThat(usuario.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    @DataSet(value = "datasets/perfiles.yml", cleanBefore = true, cleanAfter = true)
    public void shouldCreateUsuario() {
        Perfil perfil = perfilRepository.findById(1L).orElseThrow();

        Usuario usuario = Usuario.builder()
                .username("nuevousuario")
                .email("nuevousuarior@gmail.com")
                .password("cintrasena")
                .telefono("987654321")
                .perfil(perfil)
                .build();

        Usuario savedUsuario = usuarioRepository.save(usuario);
        assertThat(savedUsuario).isNotNull();
        assertThat(savedUsuario.getId()).isNotNull();
    }

    @Test
    @DataSet(value = "datasets/perfiles.yml", cleanBefore = true, cleanAfter = true)
    public void shouldUpdateUsuario() {
        Perfil perfil = perfilRepository.findById(1L).orElseThrow();

        Usuario usuario = Usuario.builder()
                .username("usuarioactualizado")
                .email("usuarioactualizado@gmail.com")
                .password("contraseña")
                .telefono("123456789")
                .perfil(perfil)
                .build();

        Usuario savedUsuario = usuarioRepository.save(usuario);
        savedUsuario.setEmail("nuevocorreo@gmail.com");
        Usuario updatedUsuario = usuarioRepository.save(savedUsuario);

        assertThat(updatedUsuario.getEmail()).isEqualTo("nuevocorreo@gmail.com");
    }

    @Test
    @DataSet(value = {"datasets/perfiles.yml", "datasets/usuarios.yml"}, cleanBefore = true, cleanAfter = true)
    public void shouldDeleteUsuario() {
        usuarioRepository.deleteById(1L);
        Optional<Usuario> usuario = usuarioRepository.findById(1L);
        assertThat(usuario).isNotPresent();
    }
}
