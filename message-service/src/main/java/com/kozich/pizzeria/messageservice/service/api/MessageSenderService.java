package com.kozich.pizzeria.messageservice.service.api;

import com.kozich.projectrepository.core.dto.MessageDTO;
import org.springframework.mail.MailException;

public interface MessageSenderService {

    void sendMessage(MessageDTO messageDTO) throws MailException;

}
