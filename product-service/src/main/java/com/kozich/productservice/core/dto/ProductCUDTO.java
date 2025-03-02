package com.kozich.productservice.core.dto;

import com.kozich.productservice.entity.CategoryEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    @NotBlank
    @NotEmpty
    @Positive
    private Integer price;

    @Size(max = 3, min = 3)
    @NotBlank
    @NotEmpty
    private String currency;

    @NotBlank
    @NotEmpty
    private CategoryEntity categoryId;

}



