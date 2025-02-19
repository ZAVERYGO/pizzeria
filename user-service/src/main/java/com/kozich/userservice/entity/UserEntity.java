package com.kozich.userservice.entity;

import com.kozich.projectrepository.core.enums.UserRole;
import com.kozich.projectrepository.core.enums.UserStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "app")
public class UserEntity {

    @Id
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "dt_Create", nullable = false)
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_Update", nullable = false)
    private LocalDateTime dtUpdate;

    public UserEntity() {
    }

    public UserEntity(UUID uuid, String email, String fio, UserRole role, UserStatus status,
                      String password, LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.email = email;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }


    public UUID getUuid() {
        return uuid;
    }

    public UserEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFio() {
        return fio;
    }

    public UserEntity setFio(String fio) {
        this.fio = fio;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public UserEntity setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserEntity setStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public UserEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public UserEntity setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

}
