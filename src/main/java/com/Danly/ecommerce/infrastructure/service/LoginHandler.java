//Vamos a personalizar los redireccionamientos de url para que al volver a logearnos, no nos redireccione a la peticion anterior al logout
package com.Danly.ecommerce.infrastructure.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class LoginHandler extends SavedRequestAwareAuthenticationSuccessHandler { //Interfaz que posee metodos los cuales podemos sobreescribir para personalizar los redireccionamientos de URL

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        AtomicReference<String> redirectURL = new AtomicReference<>(request.getContextPath()); //es una clase que proporciona un contenedor para una referencia de tipo genérico (<String> en este caso) con operaciones atómicas.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); //Trayendo todos los datos del usuario
        userDetails.getAuthorities().forEach( //.getAuthorities actua como una lista la cual usaremos para recorrer los roles
               grantedAuthority -> { //variable iteradora
                   if(grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
                       redirectURL.set("/admin"); //si es admin, que se dirija a /admin
                   }else{
                       redirectURL.set("/home"); //Si es user, que se vaya a /home
                   }
               }
        );
        response.sendRedirect(String.valueOf(redirectURL));
    }
}

/*
Las operaciones atómicas son acciones que ocurren sin interrupciones, asegurando que se realicen completamente o no.
Son vitales en entornos con múltiples procesos, ya que garantizan la consistencia de los datos compartidos,
evitando problemas como condiciones de carrera. Estas operaciones, como incrementar valores o modificar datos compartidos,
se realizan sin interrupciones de otros procesos, lo que asegura la coherencia y evita situaciones en las que múltiplesprocesos intentan modificar los mismos datos simultáneamente, lo que podría llevar a inconsistencias o pérdida de datos.
*/
