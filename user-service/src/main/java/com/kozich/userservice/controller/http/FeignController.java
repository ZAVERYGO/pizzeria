package com.kozich.userservice.controller.http;

import com.kozich.projectrepository.core.dto.UserDTO;
import com.kozich.userservice.entity.UserEntity;
import com.kozich.userservice.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.UUID;


@RestController
@Validated
@RequestMapping("/feign")
public class FeignController {

    private final UserService userService;

    public FeignController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/byEmail")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByEmail(@RequestParam(value = "mail") String mail) {

        UserEntity userEntity = userService.getByEmail(mail);

        return new UserDTO()
                .setUuid(userEntity.getUuid())
                .setEmail(userEntity.getEmail())
                .setRole(userEntity.getRole())
                .setFio(userEntity.getFio())
                .setPassword(userEntity.getPassword())
                .setStatus(userEntity.getStatus())
                .setDtCreate(userEntity.getDtCreate().atZone(ZoneId.systemDefault()).toEpochSecond())
                .setDtUpdate(userEntity.getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond());

    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@RequestParam(value = "uuid") UUID uuid) {

        UserEntity userEntity = userService.getById(uuid);

        return new UserDTO()
                .setUuid(userEntity.getUuid())
                .setEmail(userEntity.getEmail())
                .setRole(userEntity.getRole())
                .setFio(userEntity.getFio())
                .setPassword(userEntity.getPassword())
                .setStatus(userEntity.getStatus())
                .setDtCreate(userEntity.getDtCreate().atZone(ZoneId.systemDefault()).toEpochSecond())
                .setDtUpdate(userEntity.getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond());

    }

}
