package com.kozich.orderservice.service.impl;

import com.kozich.orderservice.controller.feign.client.ProductFeignClient;
import com.kozich.orderservice.core.dto.OrderCUDTO;
import com.kozich.orderservice.core.enums.OrderStatus;
import com.kozich.orderservice.entity.OrderEntity;
import com.kozich.orderservice.entity.OrderItemEntity;
import com.kozich.orderservice.repository.OrderRepository;
import com.kozich.orderservice.service.api.OrderService;
import com.kozich.orderservice.util.UserHolder;
import com.kozich.projectrepository.core.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductFeignClient productFeignClient;
    private final UserHolder userHolder;

    public OrderServiceImpl(OrderRepository orderRepository, ProductFeignClient productFeignClient, UserHolder userHolder) {
        this.orderRepository = orderRepository;
        this.productFeignClient = productFeignClient;
        this.userHolder = userHolder;
    }

    @Override
    public OrderEntity getById(UUID uuid) {
        return orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("заказа не существует"));
    }

    @Override
    public OrderEntity getById(UUID uuid, UUID userUUID) {
        return orderRepository.findByUuidAndUserUUID(uuid, userUUID)
                .orElseThrow(() -> new IllegalArgumentException("заказа не существует"));
    }

    @Override
    public Page<OrderEntity> getPage(Integer page, Integer size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<OrderEntity> getPage(UUID userUUID, Integer page, Integer size) {
        return orderRepository.findAllByUserUUID(userUUID, PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public OrderEntity create(OrderCUDTO orderCUDTO) {

        LocalDateTime date = LocalDateTime.now();
        Map<UUID, Integer> products = orderCUDTO.getProducts();
        List<OrderItemEntity> items = new ArrayList<>();
        int sum = 0;

        OrderEntity orderEntity = new OrderEntity()
                .setUuid(UUID.randomUUID())
                .setStatus(OrderStatus.NOT_PAID)
                .setUserUUID(UUID.fromString(userHolder.getUser().getUsername()))
                .setDtCreate(date)
                .setDtUpdate(date);

        for (Map.Entry<UUID, Integer> entry : products.entrySet()) {
            ProductDTO productById = productFeignClient.getProductById(entry.getKey());
            items.add(new OrderItemEntity()
                    .setUuid(UUID.randomUUID())
                    .setProductUUID(productById.getUuid())
                    .setOrderUUID(orderEntity)
                    .setNumber(entry.getValue()));
            sum += productById.getPrice() * entry.getValue();
        }

        orderEntity.setSum(sum).setOrderItems(items);

        return orderRepository.saveAndFlush(orderEntity);
    }
}
