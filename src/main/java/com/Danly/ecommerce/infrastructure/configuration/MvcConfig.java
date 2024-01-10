//SPRING MVC
package com.Danly.ecommerce.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    //configurando el metodo que nos permitirá buscar las imagenes en su respectivo directorio
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/images/"); // Ahora apunta a la carpeta resources/images
        //Esto se hizo para que la carpeta images vaya incluida en el ejecutable jar y la app acceda a las imagenes sin problemas
    }
}
