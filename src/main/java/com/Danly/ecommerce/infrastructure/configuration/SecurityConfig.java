//Especificamos que recursos estarán disponibles de acuerdo al rol de usuario
package com.Danly.ecommerce.infrastructure.configuration;


import com.Danly.ecommerce.infrastructure.service.LoginHandler;
import com.Danly.ecommerce.infrastructure.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    private LoginHandler loginHandler;

    public SecurityConfig(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){ //Permitir que el usuario se autentique
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService); //Establecemos los detalles del usuario para validar su ingreso
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //deshabilitamos los ataques csrf  (Cross-Site Request Forgery)
        //le diremos que un usuario de tipo admin solo tendrá acceso a las rutas dentro del controlador admin, lo mismo con USER, solo tendrá acceso a sus rutas respectivas
        httpSecurity.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER").anyRequest().permitAll() //.anyRequest().permitAll() =>Permite poder visualizar todas las url de todos los usuarios idependientemente de sus roles
                //.and().formLogin().loginPage("/login").defaultSuccessUrl("/login/access") //le indicamos que nos diriga al controlador del LoginController para comprobar la validacion y luego le daremos acceso por medio de un metodo POST dentro de LoginController, en este caso de "access"
                .and().formLogin().loginPage("/login").successHandler(loginHandler) //Ejecutando nuestro LoginHandler
                .and().logout().logoutSuccessUrl("/close"); //Cuando acionemos el metodo logout() que fue asignando en los templates, no redigirá al controlador para ubicarnos en la ruta /close

        return httpSecurity.build(); //retornando la sesion
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //encriptando la contraseña que ingresamos para que coincida con la de la db
    }
}

        /*
        CSRF es un tipo de ataque en el que un atacante
        puede engañar a un usuario para que realice acciones
        no deseadas en una aplicación en la que el usuario está
        autenticado. Esto se logra enviando solicitudes maliciosas
        desde un sitio web diferente al que el usuario está visitando,
        aprovechando la sesión activa del usuario en la aplicación objetivo.
        */
