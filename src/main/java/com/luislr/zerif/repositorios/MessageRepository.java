package com.luislr.zerif.repositorios;

import com.luislr.zerif.entidades.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByLocale(String locale);
}
