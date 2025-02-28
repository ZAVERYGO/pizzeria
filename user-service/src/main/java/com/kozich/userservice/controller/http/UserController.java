package com.kozich.userservice.controller.http;

import com.kozich.projectrepository.core.dto.PageDTO;
import com.kozich.projectrepository.core.enums.UserRole;
import com.kozich.userservice.core.dto.UserCUDTO;
import com.kozich.userservice.core.dto.UserDTO;
import com.kozich.userservice.core.exception.ForbiddenException;
import com.kozich.userservice.entity.UserEntity;
import com.kozich.userservice.mapper.UserMapper;
import com.kozich.userservice.service.api.UserService;
import com.kozich.userservice.util.UserHolder;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserHolder userHolder;

    public UserController(UserService userService, UserMapper userMapper, UserHolder userHolder) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userHolder = userHolder;
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getById(@PathVariable(value = "uuid") UUID uuid) {
        UserEntity userEntity = userService.getById(uuid);
        return userMapper.userEntityToUserDTO(userEntity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<UserDTO> getPage(@PositiveOrZero @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Page<UserEntity> pageEntity = userService.getPage(page, size);

        PageDTO<UserDTO> pageUserDTO = new PageDTO<UserDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<UserEntity> contentEntity = pageEntity.getContent();
        List<UserDTO> contentDTO = new ArrayList<>();

        for (UserEntity userEntity : contentEntity) {
            contentDTO.add(userMapper.userEntityToUserDTO(userEntity));
        }

        return pageUserDTO.setContent(contentDTO);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserCUDTO userCDTO) {
        userService.create(userCDTO);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UserCUDTO userCDTO,
                       @PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long dtUpdate) {
        userService.update(uuid, userCDTO, dtUpdate);
    }

    @DeleteMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long dtUpdate) {
        if (userHolder.getUserRole().equals(UserRole.ROLE_USER.name())
            && !userHolder.getUser().getUsername().equals(uuid.toString())) {
            throw new ForbiddenException();
        }

        userService.delete(uuid, dtUpdate);
    }

}
