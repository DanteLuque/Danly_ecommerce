package com.Danly.ecommerce.application.service;

import com.Danly.ecommerce.domain.User;
import com.Danly.ecommerce.domain.UserType;
import com.Danly.ecommerce.infrastructure.dto.UserDto;
import com.sun.jdi.event.ExceptionEvent;

public class LoginService {
    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public boolean existUser(String email){ //Comprobando si el usuario existe para que pueda logearse
        try {
            User user = userService.findByEmail(email); //Obtenemos un usuario por email desde la db
        }catch(Exception e){
            return false; //Si el usuario no existe, retornamos false
        }
        return true;
    }

    public Integer getUserId(String email){
        try{
            return userService.findByEmail(email).getId(); //Si se encuentra el email del usuario, significa que existe, en ese caso, obtenemos su id
        }catch(Exception e){
            return 0; //Cero, significa que el usuario no existe en la base de datos
        }
    }

    public UserType getUserType(String email){
        return userService.findByEmail(email).getUserType(); //No comprobamos si validacion por try catch ya que si estas buscando el tipo de usuario, significa que si existe
    }

    public User getUser(String email){
        try{
            return userService.findByEmail(email);//Buscamos el usuario en la db por email
        }catch(Exception e){
            return new User(); //En caso de que no se encuentre, generamos un nuevo usuario
        }
    }

    public User getUser(Integer id){ //Obtenemos usuario por id
        try{
            return userService.findById(id);
        }catch(Exception e){
            return new User();
        }
    }
}
