package com.kozich.userservice.service.api;


import com.kozich.userservice.core.dto.UserCUDTO;
import com.kozich.userservice.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    UserEntity getById(UUID uuid);

    UserEntity getByEmail(String email);

    Page<UserEntity> getPage(Integer page, Integer size);

    UserEntity create(UserCUDTO userCDTO);

    UserEntity update(UUID uuid, UserCUDTO userCUDTO, Long dtUpdate);

    boolean existsByEmail(String email);

    void delete(UUID uuid, Long dtUpdate);

}
