package com.kozich.pizzeria.messageservice.service.api;

import com.kozich.pizzeria.messageservice.core.dto.MessageCUDTO;
import com.kozich.pizzeria.messageservice.entity.MessageEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MessageService {

    Page<MessageEntity> getPage(Integer page, Integer size);

    MessageEntity create(MessageCUDTO messageDTO);

    MessageEntity update(MessageCUDTO messageDTO, UUID emailUuid, Long dtUpdate);

}
