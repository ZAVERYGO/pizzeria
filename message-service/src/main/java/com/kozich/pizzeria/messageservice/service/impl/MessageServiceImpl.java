package com.kozich.pizzeria.messageservice.service.impl;

import com.kozich.pizzeria.messageservice.core.dto.MessageCUDTO;
import com.kozich.pizzeria.messageservice.core.exception.UpdateСonflictException;
import com.kozich.pizzeria.messageservice.entity.MessageEntity;
import com.kozich.pizzeria.messageservice.repository.MessageRepository;
import com.kozich.pizzeria.messageservice.service.api.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Transactional
    @Override
    public MessageEntity create(MessageCUDTO messageDTO) {

        LocalDateTime date = LocalDateTime.now();

        MessageEntity messageEntity = new MessageEntity()
                .setUuid(UUID.randomUUID())
                .setSubject(messageDTO.getSubject())
                .setToEmail(messageDTO.getToEmail())
                .setText(messageDTO.getText())
                .setDtCreate(date)
                .setStatus(messageDTO.getStatus());

        return messageRepository.saveAndFlush(messageEntity);
    }

    @Override
    public Page<MessageEntity> getPage(Integer page, Integer size) {
        return messageRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public MessageEntity update(MessageCUDTO messageDTO, UUID emailUuid, Long dtUpdate) {

        Optional<MessageEntity> messageEntity = messageRepository.findById(emailUuid);
        MessageEntity message = messageEntity.orElseThrow(() -> new IllegalArgumentException("Не существует сообщения"));

        Long dateTime = message.getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();
        if (!dateTime.equals(dtUpdate)) {
            throw new UpdateСonflictException("Сообщение уже было изменено");
        }

        return messageRepository.saveAndFlush(message
                .setStatus(messageDTO.getStatus())
                .setText(messageDTO.getText())
                .setSubject(messageDTO.getSubject())
                .setToEmail(messageDTO.getToEmail()));
    }

}

