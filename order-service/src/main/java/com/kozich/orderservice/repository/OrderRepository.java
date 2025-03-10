package com.kozich.orderservice.repository;

import com.kozich.orderservice.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, value = "order_entity-graph")
    Page<OrderEntity> findAll(Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, value = "order_entity-graph")
    Optional<OrderEntity> findByUuid(UUID uuid);
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, value = "order_entity-graph")
    Page<OrderEntity> findAllByUserUUID(UUID userUUID, PageRequest pageRequest);
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, value = "order_entity-graph")
    Optional<OrderEntity> findByUuidAndUserUUID(UUID uuid, UUID userUUID);

}
