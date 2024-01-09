package com.Danly.ecommerce.application.service;

import com.Danly.ecommerce.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder; //Tipo de variable que proviene de spring security

    public RegistrationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword())); //encriptamos la contraseña
        userService.createUser(user); //creando un nuevo usuario

    }
}
