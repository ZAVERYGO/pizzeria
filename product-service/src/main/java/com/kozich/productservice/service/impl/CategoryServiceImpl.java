package com.kozich.productservice.service.impl;

import com.kozich.productservice.core.dto.CategoryCUDTO;
import com.kozich.productservice.core.exception.UpdateСonflictException;
import com.kozich.productservice.entity.CategoryEntity;
import com.kozich.productservice.repository.CategoryRepository;
import com.kozich.productservice.service.api.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CategoryEntity getById(UUID uuid) {
        return categoryRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("категории не существует"));
    }

    @Override
    public Page<CategoryEntity> getPage(Integer page, Integer size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public CategoryEntity create(CategoryCUDTO categoryCUDTO) {

        if (categoryRepository.getByName(categoryCUDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("категория уже существует");
        }

        LocalDateTime date = LocalDateTime.now();

        CategoryEntity userEntity = new CategoryEntity()
                .setUuid(UUID.randomUUID())
                .setName(categoryCUDTO.getName())
                .setDtCreate(date)
                .setDtUpdate(date);

        return categoryRepository.saveAndFlush(userEntity);
    }

    @Transactional
    @Override
    public CategoryEntity update(UUID uuid, CategoryCUDTO сategoryCUDTO, Long dtUpdate) {

        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(uuid);
        if (categoryEntity.isEmpty()) {
            throw new IllegalArgumentException("категории не существует");
        }

        Long dateTime = categoryEntity.get().getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();
        if (!dateTime.equals(dtUpdate)) {
            throw new UpdateСonflictException("категория была изменена");
        }

        CategoryEntity resEntity = categoryEntity.get()
                .setName(сategoryCUDTO.getName());


        return categoryRepository.saveAndFlush(resEntity);
    }

    @Transactional
    @Override
    public void delete(UUID uuid, Long dtUpdate) {

        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(uuid);
        if (categoryEntity.isEmpty()) {
            throw new IllegalArgumentException("Не существует такой категории");
        }

        Long dateTime = categoryEntity.get().getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();
        if (!dateTime.equals(dtUpdate)) {
            throw new UpdateСonflictException("категория была изменена");
        }

        categoryRepository.deleteById(uuid);
    }
}
