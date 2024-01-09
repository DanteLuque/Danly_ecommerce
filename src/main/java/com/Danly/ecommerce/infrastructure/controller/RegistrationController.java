package com.Danly.ecommerce.infrastructure.controller;

import com.Danly.ecommerce.application.service.RegistrationService;
import com.Danly.ecommerce.domain.User;
import com.Danly.ecommerce.domain.UserType;
import com.Danly.ecommerce.infrastructure.dto.UserDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    //haciendo llamado al bean RegistrationService
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String register(UserDto userDto){ //Enviando los atributos del objeto UserDto para mostrar más detalles
        return "register"; //no usamos redirect al momento de validar ya que volverá a pedir los datos y se perderá todos los datos obtenidos, aqui solo volvemos a mostrar esta vista pero con el resultado de la validacion
    }

    @PostMapping
    public String registerUser(/*User user*/ @Valid UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){ //Le pasaremos el userDto y con la anotacion Valid le diremos que lo valide, ya no le pasaremos un objeto User directamente
        // user.setDateCreated(LocalDateTime.now());
        // user.setUserType(UserType.USER);
        // user.setUsername(user.getEmail());
        if(bindingResult.hasErrors()){ //Con esto comprobamos si hay errores, si es cierto, retornará true
            bindingResult.getAllErrors().forEach(
                    e->{log.info("Error {}", e.getDefaultMessage());} //recorremos toda la lista de errores y la mostramos en consola
            );
            return "register";
        }
        registrationService.register(userDto.userDtoToUser());
        redirectAttributes.addFlashAttribute("success", "Registro exitoso"); //Parecido a Model, esto muestra un mensaje despues de que se haya completado una validacion
        return "redirect:/register";
    }
}
