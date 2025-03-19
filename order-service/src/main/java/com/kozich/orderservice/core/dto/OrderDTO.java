package com.kozich.orderservice.core.dto;

import com.kozich.orderservice.core.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDTO {

    @NotNull
    private UUID uuid;

    private Integer sum;

    private OrderStatus status;

    private UUID userUUID;

    private Long dtCreate;

    private Long dtUpdate;

    @NotEmpty
    private Map<UUID, Integer> products;
}
