package com.kozich.productservice.controller.feign.client;

import com.kozich.projectrepository.core.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;


@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/feign/byId")
    @ResponseStatus(HttpStatus.OK)
    UserDTO getUserById(@RequestParam(value = "uuid") UUID uuid);

}
