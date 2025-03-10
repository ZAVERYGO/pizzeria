package com.kozich.orderservice.repository;

import com.kozich.orderservice.entity.OrderEntity;
import com.kozich.orderservice.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

    List<OrderItemEntity> findAllByOrderUUID(OrderEntity orderUUID);
}
