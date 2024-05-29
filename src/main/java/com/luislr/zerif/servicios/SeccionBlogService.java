package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.SeccionBlog;
import com.luislr.zerif.repositorios.SeccionBlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SeccionBlogService {

    private final SeccionBlogRepository seccionBlogRepository;

    public List<SeccionBlog> findAll(){
        return seccionBlogRepository.findAll();
    }

    public Optional<SeccionBlog> findById(Long id){
        return seccionBlogRepository.findById(id);
    }

    public List<SeccionBlog> findByTituloContainsIgnoreCase(String filtro){
        return seccionBlogRepository.findByTituloContainsIgnoreCase(filtro);
    }

    public SeccionBlog save(SeccionBlog seccionBlog){
        return seccionBlogRepository.save(seccionBlog);
    }

    public void saveAll(List<SeccionBlog> list){
        seccionBlogRepository.saveAll(list);
    }

}
