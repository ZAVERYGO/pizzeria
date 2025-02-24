package com.kozich.projectrepository.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.projectrepository.core.enums.UserRole;
import com.kozich.projectrepository.core.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserDTO {

    private UUID uuid;

    @JsonProperty("mail")
    private String email;

    private String fio;

    private UserRole role;

    private UserStatus status;

    private String password;

    @JsonProperty("dt_create")
    private Long dtCreate;

    @JsonProperty("dt_update")
    private Long dtUpdate;

}
