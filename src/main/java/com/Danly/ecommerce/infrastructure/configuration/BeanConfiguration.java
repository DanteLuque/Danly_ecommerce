package com.Danly.ecommerce.infrastructure.configuration;

import com.Danly.ecommerce.application.repository.*;
import com.Danly.ecommerce.application.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class BeanConfiguration {

    //los Bean sirven para poder inyectar estas clases en cualquier parte del proyecto

    @Bean
    public ProductService productService(ProductRepository productRepository){
        return new ProductService(productRepository); //agregamos uploadFile al bean de ProductService para que nos permita inicializarlo en su constructor
    }
/*
    @Bean
    public UploadFile uploadFile(){
        return new UploadFile(); //parametros vacios ya que esta clase no posee un constructor
    }*/

    //El Bean se crea gracias al constructor dentro de los service los cuales usan una interfaz hecha en el repository
    @Bean
    public StockService stockService(StockRepository stockRepository){
        return new StockService(stockRepository);
    }

    @Bean
    public ValidateStock validateStock(StockService stockService){
        return new ValidateStock(stockService);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository){
        return new OrderService(orderRepository);
    }

    @Bean
    public OrderProductService orderProductService(OrderProductRepository orderProductRepository){
        return new OrderProductService(orderProductRepository);
    }

    //Los Bean, por defecto tiene un comportamiento "SINGLETON", al usar Scope, cambiamos ese comportamiento
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS) //Limitando el uso de este bean, solo se podrá usar a nivel de sesion
    public CartService cartService(){
        return new CartService();
    }

    @Bean
    public UserService userService(UserRepository userRepository){
        return new UserService(userRepository);
    }

    @Bean
    public RegistrationService registrationService(UserService userService, PasswordEncoder passwordEncoder){
        return new RegistrationService(userService, passwordEncoder);
    }

    @Bean
    public LoginService loginService(UserService userService){
        return new LoginService(userService);
    }

    //El constructor de este Bean no tiene parametros ya que asi fue declarado en el servicio
    @Bean
    public LogoutService logoutService(){
        return new LogoutService();
    }
}
