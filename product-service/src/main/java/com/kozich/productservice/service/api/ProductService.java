package com.kozich.productservice.service.api;

import com.kozich.productservice.core.dto.ProductCUDTO;
import com.kozich.productservice.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {

    ProductEntity getById(UUID uuid);

    Page<ProductEntity> getPageByCategory(UUID catagoryUUID, Integer page, Integer size);

    Page<ProductEntity> getPage(Integer page, Integer size);

    ProductEntity create(ProductCUDTO userCDTO);

    ProductEntity update(UUID uuid, ProductCUDTO userCUDTO, Long dtUpdate);

    void delete(UUID uuid, Long dtUpdate);

}
