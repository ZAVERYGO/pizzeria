package com.kozich.pizzeria.messageservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.projectrepository.core.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class MessageDTO {

    private UUID uuid;

    @JsonProperty("to_email")
    private String toEmail;

    private String subject;

    private String text;

    private MessageStatus status;

    @JsonProperty("dt_create")
    private Long dtCreate;

    @JsonProperty("dt_update")
    private Long dtUpdate;

}
