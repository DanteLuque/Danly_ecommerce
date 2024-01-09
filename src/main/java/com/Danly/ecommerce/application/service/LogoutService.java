package com.Danly.ecommerce.application.service;

import jakarta.servlet.http.HttpSession;

public class LogoutService {

    public LogoutService() {
    }

    public void logout(HttpSession httpSession){ //Le pasamos la variable de sesion
        httpSession.removeAttribute("iduser"); //Y eliminamos la sesion junto con el nombre que le asignamos a la variable de sesion
    }
}
