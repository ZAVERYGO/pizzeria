package com.kozich.userservice.controller.http;

import com.kozich.userservice.core.dto.LoginDTO;
import com.kozich.userservice.core.dto.RegistrationDTO;
import com.kozich.userservice.core.dto.UserDTO;
import com.kozich.userservice.mapper.UserMapper;
import com.kozich.userservice.service.api.CabinetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/cabinet")
public class CabinetController {

    private final CabinetService cabinetService;
    private final UserMapper userMapper;

    public CabinetController(CabinetService cabinetService, UserMapper userMapper) {
        this.cabinetService = cabinetService;
        this.userMapper = userMapper;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        cabinetService.registerUser(registrationDTO);
    }

    @GetMapping("/verification")
    @ResponseStatus(HttpStatus.OK)
    public void verifyUser(@NotBlank @PositiveOrZero @RequestParam(value = "code") String code,
                           @NotBlank @Email @RequestParam(value = "mail") String mail) {
        cabinetService.verifyUser(code, mail);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return cabinetService.loginUser(loginDTO);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getMyCabinet() {
        return userMapper.userEntityToUserDTO(cabinetService.getMyCabinet());
    }

}
