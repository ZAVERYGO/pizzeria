package com.kozich.productservice.service.api;

import com.kozich.productservice.core.dto.CategoryCUDTO;
import com.kozich.productservice.entity.CategoryEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CategoryService {

    CategoryEntity getById(UUID uuid);

    Page<CategoryEntity> getPage(Integer page, Integer size);

    CategoryEntity create(CategoryCUDTO userCDTO);

    CategoryEntity update(UUID uuid, CategoryCUDTO userCUDTO, Long dtUpdate);

    void delete(UUID uuid, Long dtUpdate);
}
