package com.kozich.productservice.controller.http;

import com.kozich.productservice.mapper.CategoryMapper;
import com.kozich.productservice.core.dto.CategoryCUDTO;
import com.kozich.productservice.core.dto.CategoryDTO;
import com.kozich.productservice.entity.CategoryEntity;
import com.kozich.productservice.service.api.CategoryService;
import com.kozich.projectrepository.core.dto.PageDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getById(@PathVariable(value = "uuid") UUID uuid) {
        return categoryMapper.categoryEntityToCategoryDTO(categoryService.getById(uuid));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<CategoryDTO> getPage(@PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Page<CategoryEntity> pageEntity = categoryService.getPage(page, size);

        PageDTO<CategoryDTO> pageDTO = new PageDTO<CategoryDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<CategoryEntity> contentEntity = pageEntity.getContent();
        List<CategoryDTO> contentDTO = new ArrayList<>();

        for (CategoryEntity userEntity : contentEntity) {
            contentDTO.add(categoryMapper.categoryEntityToCategoryDTO(userEntity));
        }

        return pageDTO.setContent(contentDTO);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CategoryCUDTO categoryCUDTO) {
        categoryService.create(categoryCUDTO);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody CategoryCUDTO categoryCUDTO,
                       @PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long dtUpdate) {
        categoryService.update(uuid, categoryCUDTO, dtUpdate);
    }

    @DeleteMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long dtUpdate) {

        categoryService.delete(uuid, dtUpdate);
    }

}
