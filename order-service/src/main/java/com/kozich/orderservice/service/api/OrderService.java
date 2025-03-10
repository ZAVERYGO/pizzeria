package com.kozich.orderservice.service.api;

import com.kozich.orderservice.core.dto.OrderCUDTO;
import com.kozich.orderservice.entity.OrderEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    OrderEntity getById(UUID uuid);

    OrderEntity getById(UUID uuid, UUID userUUID);

    Page<OrderEntity> getPage(Integer page, Integer size);

    Page<OrderEntity> getPage(UUID userUUID, Integer page, Integer size);

    OrderEntity create(OrderCUDTO userCDTO);

}
