package com.luislr.zerif.repositorios;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.luislr.zerif.entidades.SeccionBlog;
import com.luislr.zerif.entidades.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DBRider
public class SeccionBlogRepositoryTest {


    @Autowired
    private SeccionBlogRepository seccionBlogRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DataSet({"seccionBlog.yml", "usuarios.yml", "datasets/perfiles.yml"})
    public void shouldFindSeccionBlogById() {
        Optional<SeccionBlog> seccionBlogOptional = seccionBlogRepository.findById(1L);
        assertThat(seccionBlogOptional).isPresent();
    }

    @Test
    @DataSet({"usuarios.yml", "datasets/perfiles.yml"})
    public void shouldCreateSeccionBlog() {
        Usuario usuario = usuarioRepository.getReferenceById(3L);
        SeccionBlog seccionBlog = SeccionBlog.builder()
                .titulo("Nuevo título")
                .descripcion("Nueva descripción")
                .video("/ruta/nuevo/video.mp4")
                .fechSubida(LocalDate.now())
                .usuario(usuario)
                .build();

        SeccionBlog savedSeccionBlog = seccionBlogRepository.save(seccionBlog);
        assertThat(savedSeccionBlog.getId()).isNotNull();
    }

    @Test
    @DataSet({"seccionBlog.yml", "usuarios.yml", "datasets/perfiles.yml"})
    public void shouldUpdateSeccionBlog() {
        SeccionBlog seccionBlog = seccionBlogRepository.findById(1L).orElseThrow();
        seccionBlog.setTitulo("Nuevo título actualizado");

        SeccionBlog updatedSeccionBlog = seccionBlogRepository.save(seccionBlog);
        assertThat(updatedSeccionBlog.getTitulo()).isEqualTo("Nuevo título actualizado");
    }

    @Test
    public void shouldDeleteSeccionBlog() {
        seccionBlogRepository.deleteById(1L);
        Optional<SeccionBlog> foundSeccionBlog = seccionBlogRepository.findById(1L);
        assertThat(foundSeccionBlog).isNotPresent();
    }
}
