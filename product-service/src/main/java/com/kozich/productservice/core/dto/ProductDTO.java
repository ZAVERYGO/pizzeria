package com.kozich.productservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.productservice.entity.CategoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductDTO {

    private UUID uuid;

    private String name;

    private String composition;

    private Integer price;

    private String currency;

    @JsonProperty("category_uuid")
    private UUID categoryId;

    @JsonProperty("dt_create")
    private Long dtCreate;

    @JsonProperty("dt_update")
    private Long dtUpdate;
}
