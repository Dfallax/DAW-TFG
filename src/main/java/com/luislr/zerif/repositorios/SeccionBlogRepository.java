package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.Producto;
import com.luislr.zerif.entidades.SeccionBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeccionBlogRepository extends JpaRepository<SeccionBlog, Long> {

    public List<SeccionBlog> findByTituloContainsIgnoreCase(String filtro);
}
