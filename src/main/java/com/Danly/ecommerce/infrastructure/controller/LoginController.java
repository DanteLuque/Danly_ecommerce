package com.Danly.ecommerce.infrastructure.controller;

import com.Danly.ecommerce.application.service.LoginService;
import com.Danly.ecommerce.domain.User;
import com.Danly.ecommerce.infrastructure.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(){
        return "login";
    }

    @GetMapping("/access")
    public String access(RedirectAttributes attributes, HttpSession httpSession){ //El parametro de tipo HttpSession nos permitirá guardar la sesion del usuario e identificar que tipo de usuario es para mostrarle solo las vistas acorde a su tipo
        /*userDto.setEmail(userDto.getUsername()); //Le pasamos manualmente el email por medio del username
        log.info("UserDto email: {}", userDto.getEmail());
        log.info("UserDto pass: {}", userDto.getPassword());*/
        User user = loginService.getUser(Integer.parseInt(httpSession.getAttribute("iduser").toString())); //Trayendo la sesion del usuario

        //"Usamos RedirectAttributes envés de Model ya que esta funcion no está retornando a una vista, sinó que está haciendo "redirect"
        attributes.addFlashAttribute("id", httpSession.getAttribute("iduser").toString()); //LLevando el id del usuario logeado hacia la vista home

        if(loginService.existUser(user.getEmail())){ //Si el usuario existe
          //  httpSession.setAttribute("iduser",loginService.getUserId(userDto.getEmail())); //Buscamos el usuario en la db por medio de email y obtenemos su id para guardarlo, le damos un nombre "iduser" y asi mantenemos la sesion en toda la aplicacion con httpSession, atendiendo todas las peticiones

            if(loginService.getUserType(user.getEmail()).name().equals("ADMIN")){ //Si el tipo de usuario es igual a "ADMIN"
                return "redirect:/admin"; //lo mandamos al controlador AdminController
            }else{
                return "redirect:/home"; //lo enviaremos a la home si es un usuario normal
            }
        }
        return "redirect:/home";
    }
}
