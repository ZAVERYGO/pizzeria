package com.kozich.userservice.service.impl;

import com.kozich.projectrepository.core.dto.MessageDTO;
import com.kozich.projectrepository.core.enums.UserRole;
import com.kozich.projectrepository.core.enums.UserStatus;
import com.kozich.userservice.controller.kafka.producer.MessageProducer;
import com.kozich.userservice.core.dto.LoginDTO;
import com.kozich.userservice.core.dto.RegistrationDTO;
import com.kozich.userservice.core.dto.UserCUDTO;
import com.kozich.userservice.entity.UserEntity;
import com.kozich.userservice.service.api.CabinetService;
import com.kozich.userservice.service.api.CacheService;
import com.kozich.userservice.service.api.UserService;
import com.kozich.userservice.util.CustomUserDetails;
import com.kozich.userservice.util.JwtTokenHandler;
import com.kozich.userservice.util.UserHolder;
import com.kozich.userservice.util.VerificationCodeGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
public class CabinetServiceImpl implements CabinetService {

    private final UserService userService;
    private final UserHolder userHolder;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtHandler;
    private final MessageProducer messageSender;
    private final CacheService<UUID, Integer> cacheService;

    public CabinetServiceImpl(UserService userService, UserHolder userHolder, PasswordEncoder encoder,
                              JwtTokenHandler jwtHandler, MessageProducer messageSender, CacheService<UUID, Integer> cacheService) {
        this.userService = userService;
        this.messageSender = messageSender;
        this.userHolder = userHolder;
        this.encoder = encoder;
        this.jwtHandler = jwtHandler;
        this.cacheService = cacheService;
    }

    @Transactional
    @Override
    public UserEntity registerUser(RegistrationDTO registrationDTO) {

        if (userService.existsByEmail(registrationDTO.getEmail())) {
            throw new IllegalArgumentException("Логин уже занят. Введите другой");
        }

        UserCUDTO userCDTO = new UserCUDTO()
                .setEmail(registrationDTO.getEmail())
                .setPassword(registrationDTO.getPassword())
                .setFio(registrationDTO.getFio());

        UserEntity userEntity = userService.create(userCDTO
                .setRole(UserRole.ROLE_USER)
                .setStatus(UserStatus.WAITING_ACTIVATION));

        String verificationCode = VerificationCodeGenerator.generateVerificationCode();

        MessageDTO messageDTO = new MessageDTO()
                .setToEmail(userEntity.getEmail())
                .setSubject("Код верификации")
                .setText(verificationCode);

        cacheService.save(userEntity.getUuid(), Integer.valueOf(verificationCode));

        messageSender.sendMessage(messageDTO);

        return userEntity;
    }

    @Transactional
    @Override
    public UserEntity verifyUser(String code, String mail) {

        UserEntity userEntity = userService.getByEmail(mail);

        if (userEntity.getStatus().equals(UserStatus.ACTIVATED)) {
            throw new IllegalArgumentException("Пользователь уже верифицирован");
        }

        String codeCorrect = cacheService.get(userEntity.getUuid()).toString();

        if (codeCorrect.equals(code)) {
            UserCUDTO userDTO = new UserCUDTO()
                    .setEmail(userEntity.getEmail())
                    .setStatus(UserStatus.ACTIVATED)
                    .setRole(UserRole.ROLE_USER)
                    .setFio(userEntity.getFio());

            long epochSecond = userEntity.getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();

            return userService.update(userEntity.getUuid(), userDTO, epochSecond);
        } else {
            throw new IllegalArgumentException("Неверный код верификации");
        }
    }

    @Override
    public String loginUser(LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        UserEntity userEntity;

        if (userService.existsByEmail(email)) {
            userEntity = userService.getByEmail(email);
            if (!userEntity.getStatus().equals(UserStatus.ACTIVATED)) {
                throw new IllegalArgumentException("Вы не прошли верификацию");
            }
        } else {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        String passwordDTO = loginDTO.getPassword();
        String passwordEntity = userEntity.getPassword();
        if (!encoder.matches(passwordDTO, passwordEntity)) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        return jwtHandler.generateAccessToken(new CustomUserDetails(userEntity));
    }

    @Override
    public UserEntity getMyCabinet() {
        return userService.getById(userHolder.getUser().getUsername());
    }
}
