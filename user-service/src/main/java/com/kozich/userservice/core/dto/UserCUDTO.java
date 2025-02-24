package com.kozich.userservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.projectrepository.core.enums.UserRole;
import com.kozich.projectrepository.core.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserCUDTO {

    @NotBlank
    @Email
    @JsonProperty("mail")
    private String email;

    @NotBlank
    private String fio;

    private UserRole role;

    private UserStatus status;

    @NotBlank
    private String password;

}
