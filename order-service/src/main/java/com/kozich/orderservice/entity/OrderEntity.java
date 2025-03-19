package com.kozich.orderservice.entity;

import com.kozich.orderservice.core.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order", schema = "app")
@NamedEntityGraph(name = "order_entity-graph", attributeNodes = @NamedAttributeNode("orderItems"))
public class OrderEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "sum", nullable = false)
    private Integer sum;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "dt_Create", nullable = false)
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_Update", nullable = false)
    private LocalDateTime dtUpdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderUUID")
    private List<OrderItemEntity> orderItems;

    public OrderEntity() {
    }

    public OrderEntity(UUID uuid, Integer sum, OrderStatus status, UUID userUUID, LocalDateTime dtCreate, LocalDateTime dtUpdate, List<OrderItemEntity> orderItems) {
        this.uuid = uuid;
        this.sum = sum;
        this.status = status;
        this.userUUID = userUUID;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.orderItems = orderItems;
    }

    public UUID getUuid() {
        return uuid;
    }

    public OrderEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Integer getSum() {
        return sum;
    }

    public OrderEntity setSum(Integer sum) {
        this.sum = sum;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderEntity setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public OrderEntity setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public OrderEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public OrderEntity setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public OrderEntity setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
        return this;
    }
}
