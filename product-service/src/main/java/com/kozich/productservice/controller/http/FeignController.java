package com.kozich.productservice.controller.http;

import com.kozich.productservice.mapper.ProductMapper;
import com.kozich.productservice.service.api.ProductService;
import com.kozich.projectrepository.core.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@Validated
@RequestMapping("/feign")
public class FeignController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public FeignController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@RequestParam(value = "uuid") UUID uuid) {
        return productMapper.productEntityToProductDTO(productService.getById(uuid));

    }

}
