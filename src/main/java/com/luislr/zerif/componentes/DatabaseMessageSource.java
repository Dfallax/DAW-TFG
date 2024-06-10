package com.luislr.zerif.componentes;

import com.luislr.zerif.entidades.Message;
import com.luislr.zerif.repositorios.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class DatabaseMessageSource extends AbstractMessageSource {

    @Autowired
    private MessageRepository messageRepository;
    private final Map<String, Map<String, String>> messages = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void loadMessages() {
        List<Message> messagesList = messageRepository.findAll();
        for (Message message : messagesList) {
            messages
                    .computeIfAbsent(message.getLocale(), k -> new HashMap<>())
                    .put(message.getCode(), message.getMessage());
        }
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        Map<String, String> localeMessages = messages.get(locale.toString());
        if (localeMessages != null) {
            String message = localeMessages.get(code);
            if (message != null) {
                return new MessageFormat(message, locale);
            }
        }
        return null;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        Map<String, String> localeMessages = messages.get(locale.toString());
        if (localeMessages != null) {
            return localeMessages.get(code);
        }
        return null;
    }
}
