package com.kozich.orderservice.controller.http;

import com.kozich.orderservice.core.dto.OrderCUDTO;
import com.kozich.orderservice.core.dto.OrderDTO;
import com.kozich.orderservice.entity.OrderEntity;
import com.kozich.orderservice.mapper.OrderMapper;
import com.kozich.orderservice.service.api.OrderService;
import com.kozich.orderservice.util.UserHolder;
import com.kozich.projectrepository.core.dto.PageDTO;
import com.kozich.projectrepository.core.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

;

@RestController
@Validated
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UserHolder userHolder;

    public OrderController(OrderService orderService, OrderMapper orderMapper, UserHolder userHolder) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.userHolder = userHolder;
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getById(@PathVariable(value = "uuid") UUID uuid) {
        if (userHolder.getUserRole().equals(UserRole.ROLE_USER.name())) {
            return orderMapper.toDTO(orderService.getById(uuid,
                    UUID.fromString(userHolder.getUser().getUsername())));
        } else {
            return orderMapper.toDTO(orderService.getById(uuid));
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<OrderDTO> getPage(@PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Page<OrderEntity> pageEntity;

        if (userHolder.getUserRole().equals(UserRole.ROLE_USER.name())) {
            pageEntity = orderService.getPage(UUID.fromString(userHolder.getUser().getUsername()), page, size);
        } else {
            pageEntity = orderService.getPage(page, size);
        }

        PageDTO<OrderDTO> pageDTO = new PageDTO<OrderDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<OrderEntity> contentEntity = pageEntity.getContent();
        List<OrderDTO> contentDTO = new ArrayList<>();

        for (OrderEntity orderEntity : contentEntity) {
            contentDTO.add(orderMapper.toDTO(orderEntity));
        }

        return pageDTO.setContent(contentDTO);

    }

    @GetMapping("/user/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<OrderDTO> getPage(@PathVariable(value = "uuid") UUID userUUID,
                                     @PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Page<OrderEntity> pageEntity = orderService.getPage(userUUID, page, size);

        PageDTO<OrderDTO> pageDTO = new PageDTO<OrderDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<OrderEntity> contentEntity = pageEntity.getContent();
        List<OrderDTO> contentDTO = new ArrayList<>();

        for (OrderEntity orderEntity : contentEntity) {
            contentDTO.add(orderMapper.toDTO(orderEntity));
        }

        return pageDTO.setContent(contentDTO);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody OrderCUDTO productCUDTO) {
        orderService.create(productCUDTO);
    }

}
