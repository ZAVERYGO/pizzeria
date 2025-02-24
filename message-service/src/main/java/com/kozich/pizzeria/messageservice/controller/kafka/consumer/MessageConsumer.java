package com.kozich.pizzeria.messageservice.controller.kafka.consumer;

import com.kozich.pizzeria.messageservice.core.dto.MessageCUDTO;
import com.kozich.pizzeria.messageservice.entity.MessageEntity;
import com.kozich.pizzeria.messageservice.service.api.MessageSenderService;
import com.kozich.pizzeria.messageservice.service.api.MessageService;
import com.kozich.projectrepository.core.dto.MessageDTO;
import com.kozich.projectrepository.core.enums.MessageStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class MessageConsumer {

    private final MessageService messageService;
    private final MessageSenderService messageSender;

    public MessageConsumer(MessageService messageService, MessageSenderService messageSenderService) {
        this.messageService = messageService;
        this.messageSender = messageSenderService;
    }

    @KafkaListener(topics = "${kafka.topics.message_1}", groupId = "${spring.kafka.consumer.group-id}", concurrency = "3")
    public void sendMessage(MessageDTO message) {

        MessageCUDTO messageCUDTO = new MessageCUDTO().setText(message.getText())
                .setToEmail(message.getToEmail())
                .setSubject(message.getSubject());

        MessageEntity messageEntity = messageService.create(messageCUDTO.setStatus(MessageStatus.LOADED));
        Long dateTime = messageEntity.getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();

        try {
            messageSender.sendMessage(message);
            messageService.update(messageCUDTO.setStatus(MessageStatus.OK), messageEntity.getUuid(), dateTime);
        } catch (MailSendException e) {
            messageService.update(messageCUDTO.setStatus(MessageStatus.ERROR), messageEntity.getUuid(), dateTime);
        }
    }
}