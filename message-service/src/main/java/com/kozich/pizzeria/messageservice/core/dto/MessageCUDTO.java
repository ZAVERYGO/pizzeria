package com.kozich.pizzeria.messageservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.projectrepository.core.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class MessageCUDTO {

    @JsonProperty("to_email")
    private String toEmail;

    private String subject;

    private String text;

    private MessageStatus status;

}