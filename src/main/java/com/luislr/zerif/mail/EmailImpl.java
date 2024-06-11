package com.luislr.zerif.mail;

import com.luislr.zerif.dto.email.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailImpl implements EmailService {

    final JavaMailSender javaMailSender;
    final TemplateEngine templateEngine;


    public EmailImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }
    @Override
    public void sendMail(EmailDto emailDto) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(emailDto.getDestinatario());
            helper.setSubject(emailDto.getAsunto());
            helper.setText(emailDto.getMensaje());

            Context context = new Context();
            context.setVariable("message", emailDto.getMensaje());
            String contentHTML = templateEngine.process("email/email", context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Error al enviar el correo"+e.getMessage(), e);
        }
    }
}
