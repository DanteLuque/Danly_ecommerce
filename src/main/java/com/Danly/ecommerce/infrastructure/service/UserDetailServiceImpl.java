package com.Danly.ecommerce.infrastructure.service;

import com.Danly.ecommerce.application.service.LoginService;
import com.Danly.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService { //vamos a sobreescribir este metodo de Spring Security
    private final LoginService loginService;
    private final Integer USER_NOT_FOUND = 0;
    @Autowired //La anotación @Autowired es una característica de Spring Framework que se utiliza para realizar la inyección de dependencias de manera automática
    private HttpSession httpSession;

    public UserDetailServiceImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //con el id realizamos una validacion
        Integer idUser = loginService.getUserId(username); //obteniendo el id de un usuario por medio de su email, en este caso, está guardado como username
        if(idUser != USER_NOT_FOUND){ //si el id es diferente de 0, quiere decir que si existe
            User user = loginService.getUser(username); //obteniendo usuario
            httpSession.setAttribute("iduser",user.getId()); //Guardamos la sesion con el usuario ya encontrado
            return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getUserType().name()).build(); //Le pasamos los parametros requeridos para la autenticacion
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
