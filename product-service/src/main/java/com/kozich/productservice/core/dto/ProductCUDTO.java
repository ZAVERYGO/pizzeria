package com.kozich.productservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductCUDTO {

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    private String composition;

    @NotNull
    @Positive
    private Integer price;

    @Size(max = 3, min = 3)
    @NotBlank
    @NotEmpty
    private String currency;

    @JsonProperty("category_uuid")
    @NotNull
    private UUID categoryId;

    public ProductCUDTO setCurrency(String currency) {
        this.currency = currency != null ? currency.toUpperCase() : null;
        return this;
    }

}



