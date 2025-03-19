package com.kozich.orderservice.controller.feign.client;

import com.kozich.projectrepository.core.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/feign/byId")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO getProductById(@RequestParam(value = "uuid") UUID uuid);

}
