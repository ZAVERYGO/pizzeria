package com.kozich.productservice.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class CategoryCUDTO {

    @NotBlank
    @NotEmpty
    private String name;

    public CategoryCUDTO setName(String name) {
        this.name = name != null ? name.toLowerCase() : null;
        return this;
    }
}
