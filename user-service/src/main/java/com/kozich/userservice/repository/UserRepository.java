package com.kozich.userservice.repository;

import com.kozich.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {


    Optional<UserEntity> getByUuid(UUID uuid);
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
