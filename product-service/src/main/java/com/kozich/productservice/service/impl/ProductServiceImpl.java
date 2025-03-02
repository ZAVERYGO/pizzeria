package com.kozich.productservice.service.impl;

import com.kozich.productservice.core.dto.ProductCUDTO;
import com.kozich.productservice.core.exception.UpdateСonflictException;
import com.kozich.productservice.entity.ProductEntity;
import com.kozich.productservice.repository.ProductRepository;
import com.kozich.productservice.service.api.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity getById(UUID uuid) {
        return productRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("продукта не существует"));
    }

    @Override
    public Page<ProductEntity> getPageByCategory(UUID catagoryUUID, Integer page, Integer size) {
        return productRepository.getAllByCategoryId(PageRequest.of(page, size), catagoryUUID);
    }

    @Override
    public Page<ProductEntity> getPage(Integer page, Integer size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public ProductEntity create(ProductCUDTO productCUDTO) {

        if (productRepository.getByName(productCUDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("продукт уже существует");
        }

        LocalDateTime date = LocalDateTime.now();

        ProductEntity userEntity = new ProductEntity()
                .setUuid(UUID.randomUUID())
                .setName(productCUDTO.getName())
                .setComposition(productCUDTO.getComposition())
                .setPrice(productCUDTO.getPrice())
                .setCurrency(productCUDTO.getCurrency())
                .setDtCreate(date)
                .setDtUpdate(date)
                .setCategoryId(productCUDTO.getCategoryId());

        return productRepository.saveAndFlush(userEntity);
    }

    @Transactional
    @Override
    public ProductEntity update(UUID uuid, ProductCUDTO productCUDTO, Long dtUpdate) {

        Optional<ProductEntity> productEntity = productRepository.findById(uuid);
        if (productEntity.isEmpty()) {
            throw new IllegalArgumentException("продукта не существует");
        }

        Long dateTime = productEntity.get().getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();
        if (!dateTime.equals(dtUpdate)) {
            throw new UpdateСonflictException("продукт был изменен");
        }

        ProductEntity resEntity = productEntity.get()
                .setUuid(UUID.randomUUID())
                .setName(productCUDTO.getName())
                .setComposition(productCUDTO.getComposition())
                .setPrice(productCUDTO.getPrice())
                .setCurrency(productCUDTO.getCurrency())
                .setCategoryId(productCUDTO.getCategoryId());

        return productRepository.saveAndFlush(resEntity);
    }

    @Transactional
    @Override
    public void delete(UUID uuid, Long dtUpdate) {

        Optional<ProductEntity> productEntity = productRepository.findById(uuid);
        if (productEntity.isEmpty()) {
            throw new IllegalArgumentException("Не существует такого продукта");
        }

        Long dateTime = productEntity.get().getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();
        if (!dateTime.equals(dtUpdate)) {
            throw new UpdateСonflictException("продукт был изменена");
        }

        productRepository.deleteById(uuid);
    }

}
