package com.kozich.orderservice.mapper;

import com.kozich.orderservice.core.dto.OrderDTO;
import com.kozich.orderservice.entity.OrderEntity;
import com.kozich.orderservice.entity.OrderItemEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderMapper {

    @Mapping(target = "dtCreate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtUpdate")
    @Mapping(target = "products", qualifiedByName = "mapListToMap", source = "orderItems")
    OrderDTO toDTO(OrderEntity orderEntity);

    @Named("mapLocalDateTimeToLong")
    default Long mapLocalDateTimeToLong(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    @Named("mapListToMap")
    default Map<UUID, Integer> mapListToMap(List<OrderItemEntity> orderItemEntities) {
        Map<UUID, Integer> resultMap = new HashMap<>();
        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            resultMap.put(orderItemEntity.getProductUUID(), orderItemEntity.getNumber());
        }
        return resultMap;
    }
}
