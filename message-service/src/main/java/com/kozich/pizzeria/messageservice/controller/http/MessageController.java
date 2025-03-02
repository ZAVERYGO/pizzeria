package com.kozich.pizzeria.messageservice.controller.http;

import com.kozich.pizzeria.messageservice.core.dto.MessageDTO;
import com.kozich.pizzeria.messageservice.entity.MessageEntity;
import com.kozich.pizzeria.messageservice.mapper.MessageMapper;
import com.kozich.pizzeria.messageservice.service.api.MessageService;
import com.kozich.projectrepository.core.dto.PageDTO;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<MessageDTO> getPage(@PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Page<MessageEntity> pageEntity = messageService.getPage(page, size);

        PageDTO<MessageDTO> pageMessageDTO = new PageDTO<MessageDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<MessageEntity> contentEntity = pageEntity.getContent();
        List<MessageDTO> contentDTO = new ArrayList<>();

        for (MessageEntity messageEntity : contentEntity) {
            contentDTO.add(messageMapper.messageEntityTOMessageDTO(messageEntity));
        }

        return pageMessageDTO.setContent(contentDTO);
    }
}
