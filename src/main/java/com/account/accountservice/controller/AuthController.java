package com.account.accountservice.controller;

import com.account.accountservice.dto.user.NewPasswordDTO;
import com.account.accountservice.dto.user.PasswordChangedDTO;
import com.account.accountservice.dto.user.UserDTO;
import com.account.accountservice.mapper.ModelMapper;
import com.account.accountservice.model.user.User;
import com.account.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signup")
    public UserDTO registerUser(@RequestBody @Valid UserDTO userDTO) {
        User user = modelMapper.mapToEntity(userDTO);
        return modelMapper.mapToDTO(userService.registerUser(user));
    }

    @PostMapping("/changepass")
    public PasswordChangedDTO changePassword(@RequestBody @Valid NewPasswordDTO passwordDTO,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        userService.changePassword(userDetails.getUsername(), passwordDTO.getNewPassword());
        return new PasswordChangedDTO(userDetails.getUsername());
    }
}
