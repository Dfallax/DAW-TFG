package com.luislr.zerif.servicios;

import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Data
@Service
public class I18nService {

  private final MessageSource messageSource;

  public String getMessage(String label) {
    return messageSource.getMessage(label, null, LocaleContextHolder.getLocale());
  }

  public String getMessage(String label, Object[] args) {
    return messageSource.getMessage(label, args, LocaleContextHolder.getLocale());
  }

}
