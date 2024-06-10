package com.luislr.zerif.servicios;

import com.luislr.zerif.entidades.Message;
import com.luislr.zerif.repositorios.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void save(Message message){
        messageRepository.save(message);
    }

    public List<Message> saveAll(List<Message> list){
        return messageRepository.saveAll(list);
    }
}
