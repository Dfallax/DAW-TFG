package com.luislr.zerif.componentes;

import com.luislr.zerif.entidades.Message;
import com.luislr.zerif.servicios.MessageService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InitMessagesListener implements ApplicationListener<ApplicationReadyEvent> {

    private final MessageService messageService;

    public InitMessagesListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initMessages();
    }

    private void initMessages() {
        List<Message> messages = Stream.of(
                // Mensajes en Español
                new Message(null, "es_ES", "header.logo_alt", "logo"),
                new Message(null, "es_ES", "header.ramos", "RAMOS"),
                new Message(null, "es_ES", "header.cestas", "CESTAS"),
                new Message(null, "es_ES", "header.flores", "FLORES"),
                new Message(null, "es_ES", "header.plantas", "PLANTAS"),
                new Message(null, "es_ES", "header.eventos", "EVENTOS"),
                new Message(null, "es_ES", "header.buscar_placeholder", "Introduce el nombre del producto"),
                new Message(null, "es_ES", "header.idioma", "Idioma"),
                new Message(null, "es_ES", "header.idioma_es", "Español"),
                new Message(null, "es_ES", "header.idioma_en", "Inglés"),
                new Message(null, "es_ES", "header.iniciar_sesion", "Iniciar Sesión"),
                new Message(null, "es_ES", "header.mi_perfil", "Mi perfil"),
                new Message(null, "es_ES", "header.pedidos", "Pedidos"),
                new Message(null, "es_ES", "header.salir", "Salir"),

                // Mensajes en Inglés
                new Message(null, "en_GB", "header.logo_alt", "logo"),
                new Message(null, "en_GB", "header.ramos", "BOUQUETS"),
                new Message(null, "en_GB", "header.cestas", "BASKETS"),
                new Message(null, "en_GB", "header.flores", "FLOWERS"),
                new Message(null, "en_GB", "header.plantas", "PLANTS"),
                new Message(null, "en_GB", "header.eventos", "EVENTS"),
                new Message(null, "en_GB", "header.buscar_placeholder", "Enter product name"),
                new Message(null, "en_GB", "header.idioma", "Language"),
                new Message(null, "en_GB", "header.idioma_es", "Spanish"),
                new Message(null, "en_GB", "header.idioma_en", "English"),
                new Message(null, "en_GB", "header.iniciar_sesion", "Login"),
                new Message(null, "en_GB", "header.mi_perfil", "My profile"),
                new Message(null, "en_GB", "header.pedidos", "Orders"),
                new Message(null, "en_GB", "header.salir", "Logout")
        ).collect(Collectors.toList());

        messageService.saveAll(messages);
    }
}
