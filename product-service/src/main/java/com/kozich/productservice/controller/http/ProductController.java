package com.kozich.productservice.controller.http;

import com.kozich.productservice.mapper.ProductMapper;
import com.kozich.productservice.core.dto.ProductCUDTO;
import com.kozich.productservice.entity.ProductEntity;
import com.kozich.productservice.service.api.ProductService;
import com.kozich.projectrepository.core.dto.PageDTO;
import com.kozich.projectrepository.core.dto.ProductDTO;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }


    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getById(@PathVariable(value = "uuid") UUID uuid) {
        return productMapper.productEntityToProductDTO(productService.getById(uuid));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<ProductDTO> getPage(@PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Page<ProductEntity> pageEntity = productService.getPage(page, size);

        PageDTO<ProductDTO> pageDTO = new PageDTO<ProductDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<ProductEntity> contentEntity = pageEntity.getContent();
        List<ProductDTO> contentDTO = new ArrayList<>();

        for (ProductEntity userEntity : contentEntity) {
            contentDTO.add(productMapper.productEntityToProductDTO(userEntity));
        }

        return pageDTO.setContent(contentDTO);

    }

    @GetMapping("/category/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<ProductDTO> getPage(@PathVariable(value = "uuid") UUID uuid,
                                       @PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Page<ProductEntity> pageEntity = productService.getPageByCategory(uuid, page, size);

        PageDTO<ProductDTO> pageDTO = new PageDTO<ProductDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<ProductEntity> contentEntity = pageEntity.getContent();
        List<ProductDTO> contentDTO = new ArrayList<>();

        for (ProductEntity userEntity : contentEntity) {
            contentDTO.add(productMapper.productEntityToProductDTO(userEntity));
        }

        return pageDTO.setContent(contentDTO);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ProductCUDTO productCUDTO) {
        productService.create(productCUDTO);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody ProductCUDTO productCUDTO,
                       @PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long dtUpdate) {
        productService.update(uuid, productCUDTO, dtUpdate);
    }

    @DeleteMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long dtUpdate) {

        productService.delete(uuid, dtUpdate);
    }

}
