package com.kozich.orderservice.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "order_item", schema = "app")
public class OrderItemEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "product_uuid")
    private UUID productUUID;

    @ManyToOne
    @JoinColumn(name="order_uuid")
    private OrderEntity orderUUID;

    public OrderItemEntity() {
    }

    public OrderItemEntity(UUID uuid, UUID productUUID, OrderEntity orderUUID) {
        this.uuid = uuid;
        this.productUUID = productUUID;
        this.orderUUID = orderUUID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public OrderItemEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UUID getProductUUID() {
        return productUUID;
    }

    public OrderItemEntity setProductUUID(UUID productUUID) {
        this.productUUID = productUUID;
        return this;
    }

    public OrderEntity getOrderUUID() {
        return orderUUID;
    }

    public OrderItemEntity setOrderUUID(OrderEntity orderUUID) {
        this.orderUUID = orderUUID;
        return this;
    }
}
