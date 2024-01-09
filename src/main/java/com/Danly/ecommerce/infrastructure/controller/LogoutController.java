//Hasta este punto entiendo que "Creamos el servicio(su funcionalidad),
//luego su Bean para que sea usado en cualquier parte del proyecto
// Luego su controlador, para que pueda ser accesible desde frontend"
package com.Danly.ecommerce.infrastructure.controller;


import com.Danly.ecommerce.application.service.LogoutService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/close")
public class LogoutController {

    private final LogoutService logoutService;

    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @GetMapping
    public String logout(HttpSession httpSession){
        logoutService.logout(httpSession);
        return "redirect:/home";
    }
}
