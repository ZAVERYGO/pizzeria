package com.kozich.userservice.controller.kafka.producer;

import com.kozich.projectrepository.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    @Value("${kafka.topics.message_1}")
    private String emailTopic;

    public MessageProducer(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageDTO message) {
        kafkaTemplate.send(emailTopic, message);
    }
}
