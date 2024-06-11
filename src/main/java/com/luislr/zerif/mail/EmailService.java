package com.luislr.zerif.mail;

import com.luislr.zerif.dto.email.EmailDto;
import jakarta.mail.MessagingException;

public interface EmailService  {
    
     void sendMail(EmailDto emailDto) throws MessagingException;


}
