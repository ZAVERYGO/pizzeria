package com.kozich.productservice.repository;

import com.kozich.productservice.entity.CategoryEntity;
import com.kozich.productservice.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    Page<ProductEntity> getAllByCategoryId(PageRequest pageRequest, UUID categoryUUID);

    Optional<ProductEntity> getByName(String name);
}
