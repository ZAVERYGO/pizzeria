package com.kozich.userservice.service.api;


import com.kozich.userservice.core.dto.LoginDTO;
import com.kozich.userservice.core.dto.RegistrationDTO;
import com.kozich.userservice.entity.UserEntity;

public interface CabinetService {

    UserEntity registerUser(RegistrationDTO registrationDTO);

    UserEntity verifyUser(String code, String mail);

    String loginUser(LoginDTO loginDTO);

    UserEntity getMyCabinet();

}
