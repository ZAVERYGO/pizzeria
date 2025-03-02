package com.kozich.pizzeria.messageservice.service.impl;

import com.kozich.pizzeria.messageservice.service.api.MessageSenderService;
import com.kozich.projectrepository.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderServiceImpl implements MessageSenderService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String mailFrom;

    public MessageSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMessage(MessageDTO messageDTO) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(messageDTO.getToEmail());
        message.setSubject(messageDTO.getSubject());
        message.setText(messageDTO.getText());

        mailSender.send(message);
    }
}
